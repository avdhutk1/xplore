package com.avdhut.boot.endpoint;

import com.avdhut.boot.domain.ErrorInfo;
import com.avdhut.boot.domain.ProductOrder;
import com.avdhut.boot.exception.InvalidValueException;
import com.avdhut.boot.service.OrderService;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.InetSocketAddress;
import java.net.URI;

/**
* How spring REST works
* based on Spring MVC, it uses the DispatcherServlet internally which does the following:
* Model is resolved. Then the view is resolved depending on what the viewer wants
* In REST the same concepts is used
* The controller only resolves the resource, i.e the handler method only returns the data
* Spring offers 2 options to transform a resource into a representation for the client.
* 1. Content negotiation
* 2. Message convertor
*
* Content Negotiation
* Consists of a ContentNegotiatingViewResolver that resolves based on following:
* - extension of resource requested like .json or .xml
* if not, it fallback on Accept header of the request
* otherwise default is whatever it is configured for
* There is a ContentNegotiatingManager that can be configured to return the type you want
* You can specify the default as /application/json
*This is not the recommended way and the correct way is Message convertor
*
* HTTP message coverter
* It is more direct way and DispatcherServlet does not ferry model to views. The data is just converted
* Spring has a number of message converters. Few main ones are:
* ByteArrayHTTPMessageConverter - reads & writes byteArray and writes as application/octet-stream
* FormHTTPMessageConverter - reads and writes MultiValueMap<string,string> as application/x-www-form-urlencoded
* and MultiValueMap<String, Object> as multipart/form-data
* MappingJackson2HttpMessageConvertor - registered if jackson 2 json lib is present in classpath
*
* If the incoming request has a Accept header of json,the handler passes the data to the json convertor
* Normally to skip the model-view flow and use the message converter directly, annotations need to be added to controller
* Old versions used @ResponseBody but now new version have other annotations like @RestController, etc. see below
*
* @RestController annotation ensures that message conversion is applied to all handler methods
* At the method level, you can have the annotation @produces and @consumes to indicate which message converter to use
* Spring will first look at Content-Type and match the value it has to the method which has the same value
* in @consumes. for eg. If the content-type is 'application/json' then controller wil try to match it to a method
* handler where the @consumes annotation is the same.
*
* @RequestBody is required to convert a json to a object in the input. But response annotation is not required
*
* ######Exception Handling######
* There are three ways to handle exceptions
* 1. Per exception
* 2. per controller
* 3. Globally - for all controllers
*
* 1. Per exception : If you define your own exception and annotate it with @ResponseStatus and define the HTTP status,
* spring will send that http status
* When spring finds an exception, it first tries to find a exception handler/resolver. There are three resolvers it uses:
* 1. ExceptionHandlerExceptionResolver - tries to find methods or controller advice with @ExceptionHandler annotation
* 2. ResponseStatusExceptionResolver - tries to find exception classes that have @ResponseStatus annotation. Uses that to send response
* 3. DefaultHandlerExceptionResolver - Default for all uncaught exceptions. If it cannot find one,a default one
* called DefaultHandlerExceptionResolver is used. It converts then to HTTP status code. A lot of different exceptions
* are listed that have different HTTP status code. Check the doc for that
* All are chained and processed in the order listed
* See examples below of each type
* - ResourceNotFoundException is an example of default handling. Use Get method with id=333
* - InvalidValueException is an example of per controller. See the handleException method below. Execute the
* crete method with ID=444 tp get this exception
*  - InvalidProductNameException is an example of ResourceStatusExceptionHandler.
* Use create method with productname=exception
* - InvalidProductException is an exception of defaultHandler but also of validation using jsr330
* Use create method with id=0 to get this exception
* - InvalidProductstatusException is an example of the global exception handling - @ControllerAdvice
* Use the create method with status=unknown to test this
* see comments in the exception classes to get more details
*
* ######Adding Filters######
* Filters can be added to the Spring Boot REST application in two ways:
* 1. Using @Component and @Order - usually used to Filter for all messages
* 2. Using Java config Bean, FilterRegistrationBean - usually used to Filter for specific url
*
* 1. To add filter for all messages, define a class that implements Filter interface
* Multiple filters can be used by specifying the order
* See the examples in filter package. Order specifies the order in which the filter is invoked
* In the return path, the filters are executed in the reverse path
* The filters should have the @Component annotation to be identified, unless you use method 2
* which uses Bean config by specifying the FilterRegisterBean method explained below
 * In this case, the filter is invoked for all URL's
*
* 2. Another method is to register the bean using Java. Define the Bean with the method,
* FilterRegistrationBean<Filter-Class>.
* See the CreationAuditFilter bean for the example
* You can also specify the order in this if you register multiple beans
* You can also mix the @Component filter with Java bean config by specifying the order
* as has been done in this example
 * This can be used if filters are required to be applied for only specific URL's. The other method
 * of injecting using @Component applies filters for all URL's
 *
 * ###################################################################################################
 * ####################### Use of Open API (Swagger)#######################
 * ######Open API documentation and UI ######
 * API documentation can be generated and presented via the OpenAPI UI
 * The documentation can be in the form of a json or yaml file.
 * The UI serves the json or yaml in HTML forms
 * By default the UI is generated at http://server:por/context-path/swagger-ui. In this example it is at /swagger-ui
 * The json doc can be viewed at /v3/api-docs. To get the yaml, use /api-docs.yaml
 * You can configure the path in application.yml by setting the property
 * springdoc.swagger-ui.path=/swagger-ui-custom.html
 *
 * To generate documentation, the following is required:
 * 1. Add the maven dependency of springdoc-openapi
 * 2. The spring boot plugin also has to be configured to generate the json doc in the integration phase
 * 3. Add the annotations to add details. Open API annotations are added below
 * 4. The doc yaml file name can be configured in the maven plugin
 * See the pom file for above configuration
 * The json/yaml file generated can then be used to display swagger-ui if it is hosted on a different server
 * The main annotations are :
 * 1. Top level annotation called @OpenAPIDefinition. Only specified at the class level
 * 2. Tags are used to speperate different sections. If not used, default is the controller name
 * 3. @Operation which include other annotations like @APIResponse which includes success and error responses
 * It is specified at method level. Tags in operation that match the top level tag value are grouped together
 * 4. @Parameter - to specify the parameter details
 * 5. You can specify the annotations in the interface also. Though shown here on the controller, it is recommended
 * to have annotations in the interface. However care shoudl be taken if multiple controller implement the same
 * interface. That should be avoided
 * #####################################################################################################
 * ########################### Contract first REST implementation ################################
 * We can implement the REST using the contract first approach
 * In this case, the OpenAPI spec is first written, the code is generated using the codegen plugin
 * and the controller implementation is written
 * The contract in the example is in the resources folder - petstore.yml
 * The POM is updated with the openapi-codegen plugin. Check the POM and comments for the dependencies and
 * different configuration options
 * The plugin generates even the implementation code. Which is not required as the business logic woudl have to
 * be re-inserted in those classes. Hence, only the interfaces are generated using the 'interfaceOnly' option
 * check the pom for details
 * The plugin also generates the documentation but using spring fox lib. It also generates the code for types
 * defined in the OpenApi spec
 * The REST controller, PetController, extends the interface generated and adds teh business login
 * Currently, only one method is implemented to check if teh request reaches the method and no errors are thrown
 * See the comments in that class
*
 **/


@OpenAPIDefinition(
        info = @Info(
                title = "Product Order API",
                version = "1.0",
                description = "Rest API for Product order and http headers",
                license = @License(name = "Apache 2.0", url = "http://com.avdhut"),
                contact = @Contact(url = "http://avdhut.com", name = "Avdhut", email = "avdhut@xplore.com")
        ),
        tags = {
                @Tag(name = "Product", description = "API related to product order", externalDocs = @ExternalDocumentation(description = "product order docs")),
                @Tag(name = "Header", description = "API related to http headers", externalDocs = @ExternalDocumentation(description = "header docs")),
                })
@RestController
@RequestMapping("/services/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    private static Logger logger = LoggerFactory.getLogger(OrderController.class);

    /**
    * @RequestBody annotation is required to convert the input json to a object
    * This method also demonstrates exception (per exception) - in this case if the product name is given as 'exception',
    * it throws a exception, InvalidProductName, that is annotated with @RequestStatus and returns a http badrequest
    * response.
    * It also demonstrates the per Controller exception. When the product id is given as '444', an InvalidValueException
    * is thrown which results in a handler given in the same controller that has the @ExceptionHandler annotation
    * See the HandleException method below in this same class
     * It also demonstrates defaultHandling and validation using jsr330 annotation @Valid
     * @Valid annotation is given to a object and scaler properties inside that object have other annotations
     * If the validations fail, an exception is raised that is handled by the DefaultHandler
    */
    @Operation(summary="creates a product order",
                description="creates a product order. %productOrder% object is required",
                tags="Product",
                responses = {@ApiResponse(description = "The id of the product created",
                                            content = @Content(mediaType = "application/json")),
                            @ApiResponse(description = "Invalid product name", responseCode = "400")
                            })
    @PostMapping(path="/product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public int createOrder(@Parameter(description="Product Order object", required=true) @Valid @RequestBody ProductOrder productOrder) throws Exception{

        return  orderService.createOrder(productOrder);

    }

    /**
     * can do mapping with path variables also
     * also includes an example of default values in Request. same can be in Path variable also
     *
     * This method also demonstrates exception (per exception) - in this case if the product id is given as '333',
     * it throws a exception, ResourceNotFound, that has no annotation. Hence it is handled by the DefaultHandlerResolver
     * which usually returns a 505 http server error response
     */
    @Operation(summary="gets a product order",
            description="gets a product order. %product Id% is required",
            tags="Product",
            responses = {@ApiResponse(description = "The Product object",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductOrder.class))),
                        @ApiResponse(description = "Resource not found", responseCode = "505")
            })
    @GetMapping(path="/product/{id}", consumes = "application/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductOrder getOrder(@PathVariable int id,
                                 @RequestParam(defaultValue = "completed", name="status") String orderStatus) throws Exception{

        return  orderService.getOrder(id, orderStatus);

    }

    /**
     * This is an example on how to get specific headers.
     * Headers can be named in the @RequestHeader annotation and also has default values, required attrs
     * It also demonstrates how ResponseEntity can be used to set http headers/codes in responses
     */
    @Operation(summary="gets a http header value",
            description="gets a http header value. Default is %accept% header",
            tags="Header",
            responses = {@ApiResponse(description = "The value of the header specified",
                                        content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ResponseEntity.class))),
                    @ApiResponse(description = "Header not found", responseCode = "505")
            })

    @GetMapping("/getheader")
    public ResponseEntity<String> getSpecificHeader(@Parameter(name = "headerVal", description="header name")
                                                    @RequestHeader(name="accept", required=false) String val){
        logger.info("The request header received is {}", val);
        return new ResponseEntity<String>(String.format("received the header = %s", val), HttpStatus.OK);
    }

    /**
    * This example demostrates how to print all headers using multivalue map
    */
    @GetMapping("/listheaders")
    public ResponseEntity<String> getAllHeaders(@RequestHeader MultiValueMap<String, String> headers){

        headers.forEach((k,v)-> logger.info("the key of header is {} and the value is {}", k,v));
        return new ResponseEntity<String>(String.format("received all the headers"), HttpStatus.OK);
    }

    /**
    * This example demonstrates how to get the path of the host and port number using header
    */
    @GetMapping("/getbaseurl")
    public ResponseEntity<String> getBaseUrl(@RequestHeader HttpHeaders headers){
        InetSocketAddress host = headers.getHost();
        logger.info("the host name is {} and the port is {}", host.getHostName(), host.getPort());
        return new ResponseEntity<String>(String.format("received the base url = %s", host.getHostName() + ":" + host.getPort()), HttpStatus.OK);
    }

    /**
    * The below two examples demonstrate how to send HTTP responses other than 200
    * One way is to add the @ResponseStatus annotation as shown below
    * The other option is to use ResponseEntity shown in the next method
    * In the below method, the HTTP code 201 is sent on created
    */

    @PostMapping(path="/productwithhttpcode", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public int createOrderHttpcode(@RequestBody ProductOrder productOrder) throws Exception{

        return  orderService.createOrder(productOrder);

    }

    /**
    * The below example demonstrates the use of Response Entity
    * Response entity enables you to send extra information in addition to the main object
    * You can set http headers and also send the required http response
    * In the below case, we send the hyperlink of the object created in the header along  with 201 code
    * HTTPHeaders is a special implementation of multivaluemap
    *
    */

    @PostMapping(path="/productwithhlink", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> createOrderWithLink(@RequestBody ProductOrder productOrder, UriComponentsBuilder ucb) throws Exception{

        int productorderid  = orderService.createOrder(productOrder);

        //build the hyperlink to be returned
        HttpHeaders headers = new HttpHeaders();

        URI locationUri = ucb.path("/product")
                             .path(String.valueOf(productorderid))
                             .build()
                             .toUri();
        headers.setLocation(locationUri);

        ResponseEntity<Integer> responseEntity = new ResponseEntity<>(productorderid, headers, HttpStatus.CREATED);

        return  responseEntity;

    }

    /**
     * This demonstrates the per controller exception
     * You can specify more than one exception class
     * By default, it will send a HTTP 500 server error. Hence better to also have a @Responsestatus annotation to return
     * the required code. comment out the annotation to see the difference
     * You can return an error object or just rely on the default value returned by the spring framework.
     * Better to return an object
     * You could also return a ResponseEntity<errorInfo> object if you want more details to be sent like HTTP headers
     */
    @ExceptionHandler({InvalidValueException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleException(HttpServletRequest req, Exception ex){
        return new ErrorInfo(req.getRequestURL().toString(), ex.getMessage());
    }

}
