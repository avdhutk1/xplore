package com.audhut.j8ex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by avdhut on 3/6/18.
 */
public class DefaultIntfEx {

    /* In Java 8, interfaces can have
    1. Static methods
    2. Default methods
    Default methods are called on instances of the Interfaces,i.e. class that implements the interface
    Default methods enable to extend the Interface so that  classes that implement the interface do not need
    to implement the new methods and are automatically inherited.
    Example given below
    One advantage is that with default methods you can add utility methods and do not need a separate
    class with static methods. For eg. Collection class has a companion Collections class with static methods
    for utility methods. All this has moved to the Collection class now. Collections class is kept for
    BWC
    Functional interfaces have a lot of default methods like andThen
    What is the difference between abstract class and interfaces now
    Abstract class can have state - interfaces cannot
    A class can extend only one abstract class
    Default methods are mainly used by library developers who want to extend the existing interfaces
    But one can also use it in interfaces defined - there are two use cases for this
    1. Optional method
    Sometimes, the interfaces have a lot of methods which the implementation classes do not want to implement
    You end up with a lot of boiler plate code that has empty methods.
    To avoid this you can have a default implementation
    2. Multiple inheritence
    By implementing multiple interfaces, one can implement the default methods of all the interfaces
    This is useful if you have to implement methods you want to have default behaviour.
    **Resolution rules**
    What happens when a class implements two interfaces that have the same signature.
    If the two interfaces are independent than the class has to specifically override the method and
    call the method it wants to implement. New syntax introduced in Java 8 is X(class name).super.methodName
     But if a sub-class overrides the interface and another class implements it then the rule is :
     1. First a method inherited from class or superclass is always called.Class is preferred to an interface
     2. Sub-interface class - it is the most specific method, hence the method in the sub-interface is called

     */

    public interface Defaultex{
        default void defaultmtdh(){
            System.out.println("default method executing");
        }
    }

    public class Defaultintimpl implements Defaultex{

    }

    public interface AnotherDefault{
        default void defaultmtdh(){
            System.out.println("another default method executing");
        }
    }


    //when two interfaces have similar methods, you have to explicitly override it
    //comment the method to get an error due to ambuiguity - compiler forces to override
    public class MulDefaultintimpl implements Defaultex, AnotherDefault{

        //if you want to execute a particular method, use the class.super syntax
        public void defaultmtdh(){
            DefaultIntfEx.AnotherDefault.super.defaultmtdh();
        }

    }

    public static void main( String[] args ) {
        final Logger logger = LoggerFactory.getLogger(RefactorEx.class);

        List<Integer> nums = Arrays.asList(1,2,3,4,5);
        /* Here sort is a default method on the List interface added in Java8
        It is called on the list instance.
        naturalOrder is a static method of Comparator interface
         */
        nums.sort(Comparator.naturalOrder());

        //example of a default method
        DefaultIntfEx def = new DefaultIntfEx();
        DefaultIntfEx.Defaultex defi = def.new Defaultintimpl();
        //calling the default method
        defi.defaultmtdh();

        //when two interfaces have similar methods, you have to explicitly override it
        DefaultIntfEx.MulDefaultintimpl muld = def.new MulDefaultintimpl();
        muld.defaultmtdh();





    }


}
