package com.audhut.j8ex;

import com.audhut.j8ex.objects.Apple;
import com.audhut.j8ex.objects.Customer;
import com.audhut.j8ex.objects.Order;
import com.audhut.j8ex.objects.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * Created by avdhut on 4/2/18.
 */
public class RefactorEx {

    /* Examples on how classes can be refactored to use the Java8 features
    Replace anonymous classes with lambda's. However one needs to be careful with the differences:
    1. In Anonymous classes, the class is explicitly specified and hence 'this' refers to the class.
    In lambda's, 'this' refers to the enclosing class. Similarly, is the reference to super.
     2. In anonymous, the 'shadow' variables (i.e same variable as enclosing class) can be defined inside a class
     but in lambda you cannot - it becomes a closure
     3. Sometimes, if there are two functional interfaces with same methods, calling it can be ambiguous
     in a lambda. Lamda works on type inference and it can be confusing which one to match
     In anonymous class, the type is explicit
     */
    /* other tips:
    1. from lamda expression to method references - create smaller methods and use them in method references
    2. from imperative data processing to Streams - replace for loops, etc with Streams
    In cases, where you filter and then collect - the imperative code is not clear and mangled in loops
    instead streams offer better readability -'filter.collect'
     */



    public static void main( String[] args ) {

        final Logger logger = LoggerFactory.getLogger(RefactorEx.class);

        int shadow = 0;

        //redefining the shadow variable is fine here and there are no compile errors
        Runnable r1 = new Runnable() {
            int shadow = 2;
            @Override
            public void run() {
               logger.debug("inside the runnable anonymous class");
            }
        };

        Runnable r2 = ()-> {
            //cannot define this and gives a compile error - uncomment to see the error
            //int shadow = 2;
            logger.debug("inside the runnable lambda class");
        };

        /* two common patterns
        1. Conditional deferred execution
        This cases is common where a code frequently checks for a state of an object and then executes
        a method on the object. This is quite common in log where you check the log level and then log the message
        But other wrapper methods that are provided are also not of much help as the log message evaluation
        is done. for eg. logger.log(level, msg) -> here the msg is evaluated
        Best way for such scenarios is to write a wrapper method that takes a Functional interface with the level
        and you execute only the interface if the level is correct. Here you defer the execution of the method!
        In the below example, the client code does not check for state of object. It just passes with a functional
        interface. Thus the execution is deferred and the client is not exposed to the state
         */
        int state =1;
        executeObj(state, ()-> "a new obj");
        /*
        2. Execute around
        This pattern is useful if you find preparation and cleanup code being frequently used.
        One example is file open and cleanup
        Here the best way is to introduce a functional interface that can be passed by the client code
        and the preparation and cleanup is encapsulated in another method
         */

        /* Refactoring design patters
        1. Strategy Pattern
        In Strategy pattern, one can remove the need for creating multiple implementation classes by having an
        functional interface.
        Example below has a functional interface called ValidationStrategy but client code passes the lamda
         */
        Validator numvald = new Validator((String s)-> s.matches("[a-z]+"));
        boolean result = numvald.validate("aaa");
        logger.debug("result of numeric validator is {}", result);

        Validator lowercasevald = new Validator((String s) -> s.matches("\\d+"));
        boolean reslt = numvald.validate("bbb");
        logger.debug("result of lowercase validator is {}", reslt);

        /* 2. Template pattern
        Used to customize parts of code by allowing subclassing and implementing the abstract method
        Usually you have a super class where a method implements a logic and then calls a abstract method
        The customization is expected to extend the class and implement this method
        In the example below, the template method is refactored to accept a lamda.
         The client calls the placeOrder method that had a template method to makeCustomerHappy
          instead of subclassing to implement the method, a lamda expresssion is passed
         */
        Order ord = new Order();
        ord.placeOrder(23, (Customer c) -> logger.debug("customer called"));

        /* 3. Observer
        In observer pattern you have an interface for notify that is implemented by many different classes
        This can be replaced with a lamda expression - so the regsiterObserver method can send a lamda expression
        regsiterObserver((c)-> doSomething(c)) - instead of passing different classes
        This does not mean that lambdas are good. If the notify logic has state and is complex, it is better
        to use the implementation class
         */
        /* 4. Chain of responsibility
        COR pattern consist of an interface - say TextProcessing - and one single method - say 'process'
        The Implementation class contains a successor pointing to the same interface. This way a set of interfaces
        are chained and each method of the successor is called in a chain.
         for eg. headerTextProcesing.setSuccessor(spellcheckProcessor);
         headerTextProcesing.process(text) --> this method executes its logic first followed by the method on the sucessor
          This is similar to chaining of functions in lambda using 'and', 'andThen' methods
          In the example below, the header and spellcheck processing is done on a string
         */
        UnaryOperator<String> headerProcessor = (String text) -> "From fred and Bob " + text;
        UnaryOperator<String> spellcheckProcessor = (String text) -> text.replaceAll("lad","lambda");
        Function<String,String> textProcessor = headerProcessor.andThen(spellcheckProcessor);
        String procresult = textProcessor.apply("lad are good");
        logger.debug("example of chain of responsibility the result is => {}", procresult);

        /* 5. Factory Pattern
        This provides a good case for use of a Supplier interface which produces different objects and
        can return them to a client
         */

        //here a map holds the supplier for different objects that can be returned. For eg. Laon, stock, etc
        //in this example we will just use the apple objects
        Map<String, Supplier<Apple>> appleTypes = new HashMap<>();

        appleTypes.put("kashmiri", Apple::new);
        appleTypes.put("simla", () -> new Apple("red", 50));

        //when the client calls a Factory method to get the object we do the following
        Supplier<Apple> sup = appleTypes.get("simla"); //name is passed in the factory method
        sup.get(); //this is returned after checking for nulls!

        /* if in the factory method you need to pass multiple params, you can design your own supplier
        interface like say a 'TriFunction' which takes 3 inputs
         */
        /* Unit testing lambdas
        Lambdas return functions which can be used to test the behaviour
        focus on the behaviour of the method instead of the lambda. For eg in streams lots of intermediate
        operations are done. We need not test them. Check what the final terminating or collector function returns
        Compare two lists if the collector returns a list
         */
        /* Logging and debugging streams
        You can look at the intermediate operations in a stream by using the peek method
         */
        List<Integer> lst = Arrays.asList(1,2,3);
        List<Integer> res = lst.stream()
                            .peek(x-> System.out.println("from stream =>" + x))
                            .map(x->x+10)
                            .peek(x-> System.out.println("after map =>" + x))
                            .collect(Collectors.toList());



    }

    public static void executeObj(int state, Supplier<String> sup){

        if (state==2){
            sup.get();   //here this execution is deferred till the state is checked
        }

    }
}
