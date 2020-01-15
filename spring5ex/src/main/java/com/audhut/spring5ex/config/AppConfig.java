package com.audhut.spring5ex.config;

import com.audhut.spring5ex.impl.*;
import com.audhut.spring5ex.intf.CompactDisc;
import com.audhut.spring5ex.intf.MessageProvider;
import com.audhut.spring5ex.intf.MessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

/**
 * Created by avdhut on 21/12/18.
 * This class demonstrates how Spring config is done via a Java class
 * It is good for 3rd party lib where you cannot change some beans
 * The Configuration annotation specifies this class is a configuration of beans
 * The Beans are returned by methods whose name is the bean name
 * The Java config can also scan components instead of returning beans - just like in XML
 * This requires a annotation called componentScan similar to the XML
 * A third way is to combine XML and Java together
 * For this the annotation ImportResource is used to point to the XML config
 */

/**
 * Another feature is bean lifecycle management.
 * One can call post-initialization and pre-destruction lifecycle of a bean
 * For prototype beans, pre-destruction is not called
 * Spring helps to do this in 3 ways
 * 1. Method-based - here you specify a method that is to be called by Spring
 * 2. Interface based - the bean implements a Spring interface specific to the type of notification to be received
 * 3. JSR250 annotations for lifecycle
 * In the below examples we will use method way and jsr250 annotations
 */

/**
 * Spring manages the lifecycle of a bean in the following way
 * 1. Scans the beans, initiates them and injects properties
 * 2. Check if bean is spring aware. If it is calls setBeanName,setBeanClasslaoder, setApplicationContext
 * 3. Calls lifecycle callbacks. @PostConstruct, afterPropertySet, @Bean(initMethod=..) - one of these is called
 * 4. Calls pre-destroy callbacks. @pre-destroy, destroy, @Bean(destryMethod=..)
 */

/**
 * you can have multiple config classes, one for each module. Better way is to have such multiple classes
 * and then have one class which aggregrates this
 * This can be achieved by having a annotation like
 * @Configuration
 * @Import({CDPlayerConfig.class, CDConfig.class})
 * public class SoundSystemConfig {
 * }
 * Here the class SoundSystemConfig aggregrates all config's by using the import annotation
 * You can also add an xml to it by using the annotation below
 */

/**
 * You can define different beans for different env. i.e you define different profiles like dev, prod, qa
 * and then can have different beans injected
 * for eg. for dev, you might want to use an embedded db. but for prod, you will use the jndi to access the db
 * You can define different appconfigs for different profile
 * @Configuration
 * @Profile("dev")
 * public class DevAppConfig{
 *      @Bean
 *      //@Profile("dev") - can also define at method level
 *     public Datasource datasource(){
 *      // you instantiate embedded db required for dev env
 *     }
 * }
 * similarly you can have a 'prod' profile or a 'qa' profile. you can also have the annotation @Profile at the method
 * level. thus you can have same bean with different profiles
 * To activate the profile, you have to set the value of spring.profiles.active and/or spring.profiles.default
 * If active is not set, spring takes the default value. You can set multiple values - mostly orthogonal values
 * How to activate the profiles ? - there are different ways
 * 1. set env variables spring.profiles.active, spring.profiles.default
 * 2. set these values in web.xml for contextLoader listener and dispatcher servlet as context-param and init-param of servlet
 * 3. set in jndi
 * 4. set jvm properties
 * 5.use @ActiveProfiles annotation in integration test class
 */

/**
 * Conditional beans
 * If you want to create beans that are dependent on some env variables or other beans, you can do so
 * by adding an annotation @Conditional(class name) that is applied on @Bean methods.
 * The class implements a Conditional interface which has a match method that needs to be implemented
 */

/**
 * Autowiring and resolving ambiguities
 * If you have multiple beans implementing the same interface, you can resolve ambiguities during injection by
 * 1. Designating one bean as primary
 * 2. Adding a @Qualifier annotation - which is a better way
 * By default, the component scan mechanism sets the beanID as the class name. What if the class name changes
 * In such cases, you can explicitly give the beanId by setting a name for it in the @Component definition
 * Sometimes, even two similar beans can have similar beanId's - for eg. BeanId's are usually given based on traits
 * If two beans are similar, both can have same traits. In such cases best way is to have multiple @Qualifier annotation
 * But Java does not support multiple annotations of the same type.
 * Hence you can define a your own custom annotation of type @Qualifier and use it
 * Example include the Dessert interface and Icecream implementation
 */

/**
 * Scoping beans
 * By default Spring beans are singleton. If you want a new instance of a bean, you need to define a scope called prototype
 * The different scopes of the beans are
 * 1. Singleton
 * 2. Prototype - a new bean is created in every call
 * 3. Session - used in web app - one bean for every session
 * 4. Request - used in web app - one bean for every request
 * See Singer class for prototype
 * The @Scope annotation can also be applied to the @Bean methods below
 * see Singer class for the example
 *
 * To work with session and request scoped beans, you need some special handling.
 * Assume a ShoppingCart session scope bean in injected in a bean called StoreService
 * Now, StoreService will be created first but ShoppingCart will not be created by spring as it is session scoped -
 * unless a user comes along, the session scoped bean will not be created
 * For such, spring creates a proxy bean and injects it. But this requires the shoppingcart bean to have the following annotation
 * @Component
 * @Scope(value=WebApplicationContext.SCOPE_SESSION,proxyMode=ScopedProxyMode.INTERFACES)
 * The proxymode value needs to be set
 * This is the same for request scoped beans also
 */

/**
 * Runtime value injection
 * we have seen that you can inject static values in constructor or properties by using them in the @Bean method or
 * writing them in the xml file using the 'property' tag
 * But if you want dynamic values to be injected, there are two ways of doing it
 * 1. Property placeholders
 * 2. Spring expression language
 * Property placeholders
 * you define properties in a file and can access it via the Spring Environment class
 * Environment class has different methods like getProperty -> with key, key and default values, convert the value
 * to a specific class. You can also check if a value exists. you can also check for profiles
 * See BlankDisc example below
 * One way to inject is to get the values in the @Config Class and use the Environment class in the @Bean method to inject
 * Another way is to use placeholders like ${property-name}.
 * If component scan is use, you can inject the property source in the AppConfig class and inject the values in the bean
 * using @Value("{$property-name"}) annotation and placeholder
 * Alternative is to inject PropertySource in the Component itself. See BlankDisc class. Here the @PropertySource annotation
 * is used in the component
 *
 * Spring Expression language
 * You can also use SPEL which is more powerful. It starts with #{expression} and you can use this to refer to
 * property values , #{disc.title} or even beans and inject one bean value into another
 * #{beatles.name} or even class types with #{T(System).getcurrenttimemillis} - this uses the static method of the System class
 * But better to avoid SPEL as it is a Spring specific language and not good for production quality code
 */

//you have to comment out @ComponentScan to make use of this class
//uncomment import annotation if you want Java+XML
//@ImportResource(locations = {"classpath:beans.xml"})
@ComponentScan(basePackages = {"com.audhut"})
@Configuration
//used to inject property values dynamically. comment it out to use this class otherwise component scan is used
//@PropertySource("classpath:app.properties")
public class AppConfig {

    //need this to get env values from property files. comment it out to use this class otherwise component scan is used
    /*@Autowired
    Environment env;*/

    /*
    UnComment out the below beans if you do not want component scan
    Component scan automatically creates the beans on the basis of the @Component annotation.
    The default name of such beans is the class name with camel case.
    If you want the bean to be created in a special way - for eg. with some logic or property values injected,
    you will need to have that logic in the @Bean methods below
     */

    /*@Bean
    public MessageProvider providerannot(){
        return new MessageProviderImpl();
    }

    @Bean
    public MessageRenderer rendererannot(){
        MessageRenderer mr = new MessageRendererImpl();
        mr.setMessageProvider(providerannot());
        return mr;
    }*/

    /*
    This method demonstrates post construct lifecycle callback
    another way is for bean to implement InitializationBean interface and implement afterPropertyset method
    jsr250 annotation can also be used. Comment the first bean annotation and uncomment the second to execute the jsr250 method
    similarly you can have 'destroyMethod' attribute to a destroy bean method
    To invoke this in a application requires a change as there can be multiple points of exit
    Java provides a shutdownhook that you need to register with jvm which is a thread that is executed just before the application shuts down
    You need to call applicationcontext.registerShutdownHook and spring registers it automatically. Then the destroy method will be called
     */
    @Bean(initMethod = "init")
    //@Bean
    public Musician musician(){
        return new Musician();
    }

    //@Qualifier at bean level means the beanID is the value given
    @Bean
    @Qualifier("beatles")
    public CompactDisc beatles(){
        return new Beatles();
    }

    /*@Bean
    public CompactDisc beatles2(){
        return new Beatles();
    }*/

    /*
    The below two examples of CDPlayer demonstrates how beans can be injected in constructors
    The CDPlayer bean requires a Compact Disc. This can be injected in two ways
    The first example shows calling the beatles() method of the AppConfig class itself
    The second example shows how the spring framework calls the beatles method automatically
    But if there are two methods or beans of the same type like beatles2 - you have to explicitly call the method
     */

    /*@Bean
    public CDPlayer cdplayer() {
        return new CDPlayer(beatles());
    }*/

    //@Qualifier inside the constructor param is defined to inject the beatles bean
    @Bean
    public CDPlayer cdplayer(@Qualifier("beatles") CompactDisc cd){
        return new CDPlayer(cd);
    }

    /*
    The below example demonstrates a conditional bean
    If the condition results in a false, bean is not created. Any attempt to check the bean results in an exception
     */
    @Bean
    @Conditional({CheckEnvVar.class})
    public DependentBean dependentBean(){
        return new DependentBean();
    }

    /*
    This method demonstrates how to inject dynamic values using properties
    comment it out to use this class otherwise component scan is used
     */
    /*@Bean
    public CompactDisc blankdisc(){

        return new BlankDisc(env.getProperty("disc.title"), env.getProperty("disc.size", Integer.class));
    }*/

    //this bean is required to use property-placeholder but in spring5, it is not required. The @PropertySource annotation works
    /*@Bean
    public
    static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }*/
}
