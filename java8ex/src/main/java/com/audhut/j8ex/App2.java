package com.audhut.j8ex;

import com.audhut.j8ex.objects.Apple;
import com.audhut.j8ex.objects.Market;
import com.audhut.j8ex.objects.Market2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;

/**
 * Created by avdhut on 23/9/17.
 *
 * Main application to test the method references.
 * It also has a logger configured using logback.
 * This class has to be configured in maven to be executed
 *
 */

public class App2 {

     public static void main( String[] args ) {

        final Logger logger = LoggerFactory.getLogger(App2.class);

        logger.info("app2 class is executed");

        List<Apple> appleList = populateAppleList();

        Market market = new Market();
        Market2 market2 = new Market2();
        Apple a1 = new Apple("red", 10);
        Apple a2 = new Apple("green", 10);

        /* There are 3 ways to define a method reference. If it is static method then use the class name
        * If it is an instance method, then also you can use a class name but the instance needs to be an arg
        * If it is an instance method, the instance variable can be used
        * Below, the first example gives how the lamda and method reference are equivalent - the lambda is commented
        * The second example gives how an instance a2 is used as a reference
         */

       // List<Apple> finalList1 = market.getColoredApples(appleList, (Apple a)-> a.isGreen());
        List<Apple> finalList1 = market2.getGreenColoredApples(appleList, Apple::isGreen);
        logger.debug("green color apples are {}", finalList1);

        List<Apple> finalList2 = market2.getGreenColoredApples(appleList, a2::isSameColor);
        logger.debug("apples with same color as a2 are {}", finalList2);

        //demonstrates method ref with two args
        boolean equality =market.setWeight(a1,a2, Apple::isSameWeight);
        logger.debug("a1 and a2 are of equal weight is {}", equality);

        //demonstrates method ref for object construction
        //the first one is a supplier which accepts no args
        Apple supApple = market2.createApple(Apple::new);
        logger.debug("apple created by supplier is {}", supApple);
        //the second one is a function as the constructor takes an input - cannot use supplier here
        //notice that method reference for both is same but the input varies
        List<Integer> weights = Arrays.asList(23,24,25);
        List<Apple> applecrate = market2.createCrateOfApples(weights, Apple::new);
        logger.debug("apple created by function of weights is {}", applecrate);
        //If there are  more than one params in constructor use BiFunction
        List<String> colors = Arrays.asList("red", "green", "red");
        List<Apple> appleColorCrate = market2.createColorCrateOfApples(colors, weights, Apple::new);
        logger.debug("apple created by function of weights and colors is {}", appleColorCrate);


        /* one can develop interesting applications by having the ability to construct objects
        * of different types and with different params.
        * The example below is not a good one but demonstrates how a function can be used
        * as a reference to generate different objects.
        */
        HashMap<String, Function<Integer, Apple>> map = new HashMap<>();
        map.put("redApp", Apple::new);
        map.put("colorWght", Apple::new);
        Apple customApple = giveApple(map,"redApp","red",10);

        /* Java 8 has many static methods in interface that help to make the code succinct
        * Here the sort method takes a Comparator - the first example shows this below
        * But you can also call the 'comparing' method that returns a Comparator function
        * where the key is specified in the argument , i.e here the key is getWeight method
        * Both the below lines give the same result
        */

        appleList.sort((fa, sa)-> new Integer(fa.getWeight()).compareTo(sa.getWeight()));
        logger.debug("sorted apple list is {}", appleList);

        appleList.sort(comparing(Apple::getWeight));
        logger.debug("sorted apple list by comparing method is {}", appleList);
        //the above is equivalent to
        Comparator<Apple> comp = Comparator.comparing(Apple::getWeight);
        appleList.sort(comp);

        //chaining or composing comparators
        /* Java 8 provides many convenient methods in interfaces like Comparator, Function and Predicate
        * one can compose functions by chaining them. The ability to return Functions makes it possible to
        * chain different methods
        * One example is below where the sorting is reversed
         */

        appleList.sort(comparing(Apple::getWeight).reversed());
        logger.debug("reverse sorted apple list by composing methods is {}", appleList);
        /* you can further chain the function by comparing it with other attributes
        * for this there are other default methods provided like 'thenComparing' as shown below
         */
        appleList.sort(comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));
        logger.debug("reverse and then color sorted apple list by composing methods is {}", appleList);

         /* Composing predicates
         * You can negate a predicate. You can also chain predicates by having conditions on two attributes
         * The below example combines two predicates to have both red and heavy apples
         * Also an example of negating the predicate is shown
          */

         Predicate<Apple> redApple = (a -> a1.isGreen());
         Predicate<Apple> notRedApple = redApple.negate();

         Predicate<Apple> greenApple = (a -> a2.isGreen());
         Predicate<Apple> greenAndHeavyApple = greenApple.and(a2::isHeavy);
         logger.debug("green and heavy apple is {}" + greenAndHeavyApple.test(a2));

         /* compose functions
         * one can compose functions with 'andthen' or 'compose'
         * In the first case, it is g(f(X)) and in the second case it is f(g(x))
         * see the example below
          */
         Function<Integer, Integer> f = x->x+1;
         Function<Integer, Integer> g = x->x*2;

        Function<Integer, Integer> goff = f.andThen(g); //g(f(x)
         int z = goff.apply(1);
         logger.debug("function using addThen - the answer is {}", z );

         Function<Integer, Integer> fofg = f.compose(g); //f(g(x)
         z = fofg.apply(1);
         logger.debug("function using compose - the answer is {}", z );

         /* such examples are useful for chaining functions like adding header, checkSpelling, addFooter to a Letter object
         * similar to chaining functions in maths
          */


    }

    private static Apple giveApple(Map<String, Function<Integer, Apple>> map, String key, String color, Integer weight){
        return map.get(key).apply(weight);
    }


    private static List<Apple> populateAppleList() {

        List<Apple> appleBasket = new ArrayList<>();

        for (int i=0; i<5; i++){
            Apple ax = new Apple();
            if (i%2==0) {
                ax.setColor("green");
                ax.setWeight(i);
            }
            else {
                ax.setColor("red");
                ax.setWeight(i);
            }
            appleBasket.add(ax);
        }

        return appleBasket;
    }

}
