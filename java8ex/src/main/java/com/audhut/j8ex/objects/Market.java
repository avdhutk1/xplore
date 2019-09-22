package com.audhut.j8ex.objects;

import com.audhut.j8ex.intf.OrderingSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

/**
 * Created by avdhut on 30/7/17.
 * A class to test different methods of a functional interface introduced in java8
 */
public class Market {

    /* This method accepts a Predicate. A Predicate is only used to test a condition
    and hence returns only a boolean
     */
    public <T> List<T> getColoredApples(List<T> apples, Predicate<T> colorTest){

        List<T> selectedApples = new ArrayList<>();

        for (T a : apples){
            //here the predicate method only returns a boolean
            if (colorTest.test(a)){
                  selectedApples.add(a);
            }
        }

        return selectedApples;
    }

     /* This method accepts a Function. A Function is generally used to transform an input and
     return the modified input.Hence it takes 2 arguments - T for input processing on which
     the transformation is applied and R is the return value.
     */

    public <T, R> List<R> getWeightOfApples(List<T> apples, Function<T, R> weightCalc){

        List<R> selectedApples = new ArrayList<R>();

        for (T a : apples){
            //here the function method executes the apply method which should return a value
            selectedApples.add(weightCalc.apply(a));
        }

        return selectedApples;
    }

    /* This method accepts a Consumer. A Consumer is generally used to process the input but
    not return a value. Hence it returns a void. Typical use case can be storing in DB,
     adding values to a cache or just logging
     */

    public <T> void eatApples(List<T> apples, Consumer<T> fatBoy){

        for (T a : apples){
            //here the function method executes the accept method which 'consumes' the input
            fatBoy.accept(a);
        }

    }

    /* This method accepts a Supplier. A Supplier is generally used to process the input and
    return a value. It does not accept an argument. Typical use case can be creating objects, like a factory method.
    A Callable is a Supplier interface
     */

    public <T> T getCrateOfApples(Supplier<T> appleCrate){

             //here the function method executes the get method which processes and supplies the output
            return appleCrate.get();
    }


    /* This method accepts a specialized version of Function. A Function accepts and returns an object. This adds
     an overhead when dealing with wrappers for primitives. Hence to prevent this overhead there are specialized
     functional interface.
     */

    public <R> R setWeight(int weight, IntFunction<R> weightCalc){

        return weightCalc.apply(weight);
    }

    /* There are specialized methods that accept two arguments. These methods have prefix 'Bi'. For eg. BiPredicate,
    BiFunction, etc.
    Here is a one that compares two objects and checks if they are the same.
    */

    public <T, U> boolean setWeight(T a1, U a2, BiPredicate<T, U> checkequals){

        return checkequals.test(a1, a2);
    }


    /* This demonstrates how an API exposes a functional interface defined by you
     Here the OrderginSystem is a functional interface with no implementation.
     The implementation is passed as a lambda
      */
    public Order placeOrder(Apple a, OrderingSystem ordSys) {

        return ordSys.createOrder(a);

    }

}

