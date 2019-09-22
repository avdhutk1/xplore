package com.audhut.j8ex;

import com.audhut.j8ex.objects.Apple;
import com.audhut.j8ex.objects.Market;
import com.audhut.j8ex.objects.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Main application to test the various lambdas.
 * It also has a logger configured using logback
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        final Logger logger = LoggerFactory.getLogger(App.class);

        logger.info("app class is executed");

        Market market = new Market();
        Apple a1 = new Apple();
        a1.setColor("green");
        a1.setWeight(5);

        Apple a2 = new Apple();
        a2.setColor("red");
        a2.setWeight(10);

        int i = 7;

        List<Apple> appleList = populateAppleList();


        /**
         these examples explain the different types of lambdas available in the java lib
        each lambda passed need to match the required interface.
        first test the Predicate
        */

        /*there are two statements below. The first one uses an explicit type while the second does not.
        In this case, the JVM infers the type based on functional discriptor and the surrounding context.
        Notice that the variable 'a' is used without the type. This makes the code even more concise
       */
        List<Apple> finalList1 = market.getColoredApples(appleList, (Apple a) -> "green".equals(a.getColor()));
        List<Apple> finalList2 = market.getColoredApples(appleList, a -> "green".equals(a.getColor()));
        logger.debug("colored list is {}", finalList1);
        logger.debug("colored list is {}", finalList2);

        //Test the Function
        // used to transform/map a input.
        List<Integer> appleWeights = market.getWeightOfApples(appleList, (Apple a) -> a.getWeight());
        logger.debug("apple weight list is {}", appleWeights);

        //Test the Consumer
        //used to 'consume' a input value and not produce a return value
        market.eatApples(appleList, a -> logger.debug("apple eaten is {}", a2));

        //Test the Supplier
        //used to create objects like a factory method, Callable interface
        Apple a6 = market.getCrateOfApples(()-> appleList.get(0));
        logger.debug("apple supplied from crate is {}", a6);

        //Test the IntFunction which avoid the overhead of autoboxing.
        //Here the input is integer and the transformed object is returned
        //also demonstrates multiple statements in lamda
        Apple a5 = market.setWeight(i, (int k) -> {a2.setWeight(k);
                                                    return a2;});
        logger.debug("a5 is modified to {}", a5.getWeight());

        //Test the BiPredicate which accepts two inputs
        boolean equality =market.setWeight(a1,a2, (a3,a4)-> a4.getWeight()==a3.getWeight());
        logger.debug("a1 and a2 are of equal weight is {}", equality);

        //This demonstrates closure where i is a local variable passed in the lambda
        //gives a compile error if you try to modify the local variable i. It is implicitly final
        //market.eatApples(appleList, a -> i=6);

        //Demonstrates how a functional interface defined by you can be called.
        // in this, the implementation of OrderingSystem is passed as a lambda
        Order ord = market.placeOrder(a2, (Apple a) -> {Order or = new Order();
                                                            or.setApple(a);
                                                            return or;});
        logger.debug("The order is {}", ord);

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
