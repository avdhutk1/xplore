package com.audhut.j8ex;

import com.audhut.j8ex.objects.Dish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

/**
 * Created by avdhut on 1/1/18.
 */
public class StreamEx2 {

    public static void main(String[] args){

        /* Collectors - collecting data with streams
        Collectors are terminal operations that collect each processed data from a stream into a 'Collector'
        All terminal operations we saw previously in StreamEx were Collectors - that implement a Collector interface
        They are usually a reduction operation. The 'collect' method triggers a reduction operation
         that accepts a Collector interface.
         The methods like toList, groupingBy, etc are examples of static factory methods in the interface
         You can also define your own collector
         */
        final Logger logger = LoggerFactory.getLogger(StreamEx2.class);
        logger.info("StreamEx2 class executed");

        List<Dish> dshLst = StreamEx.getDishes();

        /*below example is how to group dishes by their type
        It demonstrates how functional programming is more on what to do instead of 'how' to do
        The groupingBy method takes the key as the input - here it is the Dish type
         */
        Map<Dish.Type, List<Dish>> dishmap = dshLst.stream()
                                             .collect(groupingBy(Dish::getType));

       logger.debug("the grouping map is {}", dishmap);

        /* Predefined collectors can be classified as :
        1. Reducing and summarizing stream to a single value
        2. grouping
        3. partitioning - special case of grouping using a predicate
         */
        //Reducing and summarizing
        long numdsh = dshLst.stream().collect(counting());  //demonstrates the counting method in Collector
        numdsh = dshLst.stream().count(); //a better way to count

        //Finding max and min
        Comparator<Dish> dishCalComp = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> maxcalDsh = dshLst.stream().collect(maxBy(dishCalComp)); //can also use the mapToInt example given in StreamEx
        logger.debug("max cal dish is {}", maxcalDsh.get());

        //summarizing operations
        int totalCal = dshLst.stream().collect(summingInt(Dish::getCalories));
        logger.debug("total cal of dishes is {}", totalCal);

       double avgcal= dshLst.stream().collect(averagingInt(Dish::getCalories));
        logger.debug("the avg calories in dishes is {}", avgcal);

        /*Sometimes you want two or more values in one operation
        summarizing functions provide a lot of stats in the IntSummaryStatistics object
        This object contains a lot of stats like count, avg, sum, etc as seen in the example below
         */

        IntSummaryStatistics menustats = dshLst.stream().collect(summarizingInt(Dish::getCalories));
        logger.debug("the stats obtained through summarizing obj is - avg is {}, sum is {}, count is {}, max is {}, min is {}",
                menustats.getAverage(), menustats.getSum(), menustats.getCount(), menustats.getMax(), menustats.getMin());

        //joining strings
        String dshstr = dshLst.stream().map(Dish::getName).collect(joining(","));
        logger.debug("the concatenated names of dish is {}", dshstr);

        /* There is a generalized reducing method that can be used instead of the
        specific ones explained above. For eg. The max calorie or sum can be also done
        using the generalized reducing method
         */
        /*this example takes a identity function (function that returns the same input value) as the initial value
        a mapper and a Binary operator
         */
        /* collect vs reduce. The reduce function can also give similar results but it will have to be
        mutable which is not good for parallel operations
         */
        int totcal = dshLst.stream().collect(reducing(0,Dish::getCalories, (a,b)->a+b));
        //this example does not have initial value and hence returns an Optional object
        Optional<Dish> maxcaldsh = dshLst.stream().collect(reducing((i,j)->i.getCalories()>j.getCalories()? i:j));
        //another way - here 3 arguments are given
        totalCal = dshLst.stream().collect(reducing(0,Dish::getCalories, Integer::sum));

        /* There are multiple ways of performing the reduce operations. reducing, reduce and using mapToInt and
        sum are ways in which the same effect can be obtained.
        reduce, mapToInt and sum are direct methods on stream and hence are concise. BUt reducing gives flexibility and
        abstraction where you can have your own functions. for eg. instead of joining, you can have a reduce function
        that concatenates strings.
        We need to explore all possible ways and find the best way. For eg. above, using Integer results in auto-boxing
        overhead. Hence using mapToInt is more effective.
         */

        /* Grouping functions
        The very first example showed a grouping example. But what if you want a different classification
        grouping functions take a classification function
         */
        Map<Dish.CalorificLevel, List<Dish>> dshCalLevel = dshLst.stream()
                                                            .collect(groupingBy(
                                                                    d->{
                                                                        if (d.getCalories()<400)
                                                                            return Dish.CalorificLevel.DIET;
                                                                        if (d.getCalories()<700)
                                                                            return Dish.CalorificLevel.NORMAL;
                                                                         else
                                                                             return Dish.CalorificLevel.FAT;
                                                                    }
                                                            ));

        logger.debug("the calorific values dishes are {}", dshCalLevel);

        /* above only one classification function is passed to the groupingBy method
         The same method can also accept another argument - another classification function to
         get multilevel grouping
         Think in terms of 'buckets'. The first classification creates bucket by the first key,DishType
         The next creates buckets inside this bucket using the second classification key
         */
        Map<Dish.Type, Map<Dish.CalorificLevel, List<Dish>>> dshCalMultiLevel = dshLst.stream()
                                                .collect(groupingBy(Dish::getType,
                                                        groupingBy(
                                                        d->{
                                                            if (d.getCalories()<400)
                                                                return Dish.CalorificLevel.DIET;
                                                            if (d.getCalories()<700)
                                                                return Dish.CalorificLevel.NORMAL;
                                                            else
                                                                return Dish.CalorificLevel.FAT;
                                                        }
                                                        )));
        logger.debug("the multilevel calorific values dishes are {}", dshCalMultiLevel);

        /* collecting data in sub-groups.
        You can get stats in sub-groups by first classifying them and them calling the data functions.
        The second collector passed to groupingBy can be any function like counting, etc.
         */
        Map<Dish.Type, Long> dshTypeCount = dshLst.stream()
                                .collect(groupingBy(Dish::getType,counting()));

        logger.debug("collecting data in sub-groups is {}", dshTypeCount);

        //another example where grouping and max is used

        Map<Dish.Type, Optional<Dish>> dshTypeCal = dshLst.stream()
                                                          .collect(groupingBy(Dish::getType,
                                                                  maxBy(comparingInt(Dish::getCalories))));
        logger.debug("grouping dish based on max cal is {}", dshTypeCal);

        /*to avoid the Optional wrapping in the results returned, you can use
        collectingAndThen methods which is adapting the results. You can use this method to convert a result
        to a different type returned by the collector
        Here a collector (collectionAndThen) is nested inside other (groupingBy). Thus you can nest multiple collectors
        The classification creates a sub-stream and the nested collector acts on each sub-stream.
        In each sub-stream, it is first maxed and then the transformation function (Optional-get) applied.
         Finally they are grouped by the enclosing collector (groupingBy). i.e it works from inside out.
         */

        Map<Dish.Type, Dish> dshTypeCal2 = dshLst.stream()
                                                 .collect(groupingBy(Dish::getType,
                                                          collectingAndThen(maxBy(comparingInt(Dish::getCalories)),
                                                         Optional::get)));

        logger.debug("grouping dish based on max cal without optional is {}", dshTypeCal2);

        /*another nested collector example is of mapping where it accepts a function to map and another
        for transformation.Here it creates Set from the CalorificValue
        */

        Map<Dish.Type, Set<Dish.CalorificLevel>> dshTypeCalLevel = dshLst.stream()
                                                                    .collect(groupingBy(Dish::getType,
                                                                    mapping(
                                                                            d->{
                                                                                if (d.getCalories()<400)
                                                                                    return Dish.CalorificLevel.DIET;
                                                                                if (d.getCalories()<700)
                                                                                    return Dish.CalorificLevel.NORMAL;
                                                                                else
                                                                                    return Dish.CalorificLevel.FAT;
                                                                            },toSet())));
        logger.debug("the mapping example of collector is {}", dshTypeCalLevel);

        /* Partitioning
        Partitioning is a type of grouping where the key is a boolean, i.e it takes a predicate function
         */

        Map<Boolean, List<Dish>> partMenu = dshLst.stream()
                                            .collect(partitioningBy(Dish::isVeg));
        logger.debug("the partitioned menu is {}", partMenu);

        /* you can also do the same using the previous example of predicates. The difference is that
         in partitioning you get both the booleans,i.e veg and non-veg in this example
          */
        // you can chain collectors by further grouping
        Map<Boolean, Map<Dish.Type, List<Dish>>> partGrpMenu = dshLst.stream()
                                                               .collect(partitioningBy(Dish::isVeg,
                                                                       groupingBy(Dish::getType)));
        logger.debug("the partitioned and grouped menu is {}", partGrpMenu);

        //another example
        Map<Boolean, Dish> maxcalPartMenu = dshLst.stream()
                                            .collect(partitioningBy(Dish::isVeg,
                                            collectingAndThen(maxBy(comparingInt(Dish::getCalories)),Optional::get)));
        logger.debug("the max cal partitioned menu is {}", maxcalPartMenu);

        //you can also have multi level partitioning
        Map<Boolean, Map<Boolean, List<Dish>>> maxcalPart = dshLst.stream()
                                                    .collect(partitioningBy(Dish::isVeg,
                                                            partitioningBy(d->d.getCalories()>400)));
        logger.debug("the multilevel partitioning is {}", maxcalPart);

        //partitioning a stream of numbers into prime and non-prime
        Map<Boolean, List<Integer>> partPrime = IntStream.rangeClosed(2,15).boxed()
                                                .collect(partitioningBy(i->isPrime(i)));
        logger.debug("partitioned primes are {}", partPrime);

        /*summary of static methods in collector interface
        toList, toSet,toCollection,
        counting, summingInt, averagingInt, summarizingInt,
        joining, maxBy, MinBy, reducing,
        collectingAndThen,groupingBy, partitioningBy
         */

        /* Collector  interface- Collector<T, A, R> where
        T - is the type of items to be collector
        A- is the type of accumulator- object in which the partial result will be accumulated
        R - is the final type of object resulting from the collect operation
        The methods are :
        Supplier - creates an empty accumulator. For eg. in toList it can create a List
        Accumulator - returns a function that performs the reduction operation.eg. in toList - it returns a function that add items and returns a partial list
        Finisher - returns a function that is invoked at the end of accumulation process. eg. in toList, the returns type is asmae as accumulator, i.e a list
        These 3 functions are good enough but you also have
        combiner - used for parallelizing streams. It combines accumulators of different sub-streams
        characteristics - returns a enum - unordered, concurrent, identity_finish
         */
        //calling the custom collector
        Map<Boolean, List<Integer>> primeList = IntStream.rangeClosed(2,100).boxed()
                                                .collect(new PrimeNumbersCollector());
        logger.debug("partitioned primes with custom collector are {}", primeList);


    }

    public static boolean isPrime(int candidate){
        int candidateroot = (int)Math.sqrt((double)candidate);
        return IntStream.range(2,candidateroot).noneMatch(i->candidateroot%i==0);
    }
}
