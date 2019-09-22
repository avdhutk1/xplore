package com.audhut.j8ex;

import com.audhut.j8ex.objects.Dish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * Created by avdhut on 19/11/17.
 * Demonstrates stream features
 * It also has a logger configured using logback.
 * This class has to be configured in maven to be executed
 */
public class StreamEx {

    public static void main(String[] args){

        final Logger logger = LoggerFactory.getLogger(StreamEx.class);
        logger.info("StreamEx class executed");

        List<Dish> dishes = getDishes();

        /* streams enable to write code in a declarative way, i.e specify *what* you want to achieve
        instead of *how* to achieve. similar to sql
        Streams are sequence of elements from a source that support data processing capabilities.
        Source can be Collections, arrays which are finite or even infinite like a stream of prime numbers
        or I/O resources such as that from a file
        Different data processing operations are filter, map, reduce/limit, find, match, sort and many more and
        they can be executed in parallel or sequentially
        filer, map are stateless while methods like sorted,distinct, reduce are stateful
         */
        /* first example of a stream
        * It first filters the list to get the high calorie dishes, then it sorts them, transforms to
        * get the names of dishes, limits it to a few and terminates the stream by collecting the result.
        * Note the use of static imports for comparing and converting to list.
        * Streams have two types of operations
        * 1. Intermediate - those that operate on the data like filter, sort,etc
        * 2. Terminal - those that closes the pipeline and produces the output. either returns a list/map,
        * integer (count) or just void. forEach, collect, count are some examples of terminal operations
        * */

        List<String> highCalDishes = dishes.stream()
                                        .filter(d-> d.getCalories()> 200)
                                        .sorted(comparing(Dish::getCalories).reversed())
                                        .map(Dish::getName)
                                        .limit(3)
                                        .collect(toList());
        logger.debug("The high calorie dishes are {}", highCalDishes);

        /* stream can be processed only once. In the example below, an exception is thrown when
        the stream is attempted to be processed a second  time
         */
        Stream<Dish> strm = dishes.stream();
        strm.limit(3);
        // uncomment the below line to see the exception - stream has already been processed
        //strm.limit(5);

        /* examples of filtering
        Filtering with predicate
         */

        List<Dish> dishLst = dishes.stream().filter(Dish::isVeg).collect(toList());
        logger.debug("The filtered veg dishes are {}", dishLst);

        //filtering to get distinct values of all numbers divisible by 2
        //also demonstrates another terminal operation that returns null - forEach
        List<Integer> intLst = Arrays.asList(1,2,2,4,4,8,8,5,5);
        logger.debug("distict numbers are");
        intLst.stream().filter(i->i%2==0).distinct().forEach(System.out::println);
        //without distinct
        logger.debug("non-distinct number are");
        intLst.stream().filter(i->i%2==0).forEach(System.out::println);

        //truncating a stream, i.e limiting to specific number
        dishLst = dishes.stream().filter(c->c.getCalories()>200).limit(3).collect(toList());
        logger.debug("The truncated list of dishes are {}", dishLst);
        dishLst = dishes.stream().filter(c->c.getCalories()>200).collect(toList());
        logger.debug("The non-truncated list of dishes are {}", dishLst);

        //skipping a stream. skip is complementary to limit. here it skips the first two and returns the rest
        dishLst = dishes.stream().filter(c->c.getCalories()>200).skip(2).collect(toList());
        logger.debug("The skipped list of dishes are {}", dishLst);

        /*examples of mapping
        mapping transforms a stream to create a new stream. Is equivalent to creating a new 'version
         of the stream rather than modifying. Each element of the stream is transformed
         */
        //in this example, the dish stream is mapped to get the names of the dishes
        List<String> dishNames = dishes.stream().map(Dish::getName).collect(toList());
        logger.debug("dishes mapped to get names are {}", dishNames);
        //get the length of the names of dishes
        List<Integer> dishNamesLgt = dishes.stream().map(Dish::getName).map(String::length).collect(toList());
        logger.debug("dishes mapped to get names are {}", dishNamesLgt);

        /*flatenning strings
        what if one wants to merge a list of words and get a list of all characters,
        maybe all unique characters
        */
        List<String> strLst = Arrays.asList("Hello", "World", "Good", "Morning");
        /* If you try to use Split function, it splits each word into an array and hence
        the map function gives a Stream<String[]>. This results in a List of String[] instead of
        a list of string that we want.
        Uncomment the below line to see the error
         */
        //List<String> flatstr = strLst.stream().map(s->s.split("")).collect(toList());
        List<String[]> stremstr = strLst.stream().map(s->s.split("")).collect(toList());

        //how to flatten a string then
        //what if we convert a Array into a stream
        strLst.stream().map(s->s.split(""))
                       .map(Arrays::stream)  //maps each array into a separate stream
                       .collect(toList());
        //This is also not correct as it results in a Stream<Stream<String>>
        //flatmap has the effect of not matching each array into a stream but with the contents of a array
        List<String> flatStr = strLst.stream().map(s->s.split(""))
                                    .flatMap(Arrays::stream)
                                    .collect(toList());
        logger.debug("flatenned string is {}", flatStr);

        //Given 2 list how to construct a list of tuple
        List<Integer> lst1 = Arrays.asList(1,2,3);
        List<Integer> lst2= Arrays.asList(4,5);
        //use of flatmap. If only map is used,it returns a stream of Integer[],Stream<Stream<Integer[]>>. Flatmap gives a list of [], i.e a tuple
        List<int[]> tupleLst = lst1.stream()
                                   .flatMap(i->lst2.stream().map(j-> new int[]{i,j}))
                                    .collect(toList());
        logger.debug("list of tuples is {}", tupleLst.toString());

        /* Finding and matching
        Streams enable finding and matching contents based on a predicate.
        AnyMatch,AllMatch,noneMatch, findFirst, findAny are some examples. They use shortcircuiting. i.e evaluates only
        till the first match is found. Similar to && or || operator.
        Useful for streams that are infinite.
        They are also terminal operations
         */
        boolean foundVeg= dishes.stream().anyMatch(Dish::isVeg);
        logger.debug("It is {} that we found a veg dish ", foundVeg);

        boolean lessCal = dishes.stream().allMatch(d->d.getCalories()<1000);
        logger.debug("It is {} that we found all dishes with less calories ", lessCal);

        boolean highCal = dishes.stream().noneMatch(d->d.getCalories()>1000);
        logger.debug("It is {} that we did not find any dish with more calories", highCal);

        //finding a match - needs to be combined with other functions like filter
        //Use of optional if no dish is found and note the terminal operation
        Optional<Dish> vegDsh = dishes.stream().filter(Dish::isVeg).findAny();
        logger.debug("It is {} that a veg dish was found and it is {}", vegDsh.isPresent(), vegDsh.get());

        //finding first match
        List<Integer> numLst = Arrays.asList(1,2,3,4,5);
        Optional<Integer> firstSquareDivisibleByThree = numLst.stream()
                                                        .map(x->x*x).filter(i->i%3==0).findFirst();
        logger.debug("the first squre divisble b 3 is {}", firstSquareDivisibleByThree.isPresent());

        //reduce operations
        /*  Reduce operations are used to reduce a stream to a single value
        operations like sum or max that produce a single value use these operations
         */
        /* here sum of an array of number is produced.
         Reduce accepts two params  - a initial value and the BinaryOperator<T> that takes two inputs
          */
        Integer sumNo = numLst.stream().reduce(0, (a,b)->a+b);
        /* a variation of Reduce that does not accept the initial value. Hence returns a optional.
        * Also note the use of the sum method in Integer class*/
        Optional<Integer>  sumNum = numLst.stream().reduce(Integer::sum);
        logger.debug("The sum of number is {} and with optional it is {}", sumNo, sumNum.get());

        long cnt = numLst.stream().count();
        logger.debug("the count is {}", cnt);


        Optional<Dish> maxCalDish = dishes.stream().max(comparing(Dish::getCalories));
        logger.debug("the max calorie dish is {}", maxCalDish.get());

        /* For numeric streams, there is an overhead involved due to auto-boxing.
        Instead of the above method - reduce(Integer::sum), it would be nice to have a method called sum().
        But the map produces a Stream<T>, on which you cannot do a 'sum'.
        StreamAPI supplies primitive stream specializations that support specialized methods to work with stream of numbers
        IntStream,DoubleStream, LongStream are such specialized primitive classes and mapToInt are specialized methods.
         */
            int totalCal1 = dishes.stream()
                                 .map(Dish::getCalories)  //returns Stream<T> hence cannot use sum() method
                                 .reduce(0,Integer::sum); //overhead of auto-boxing

            int totalCal = dishes.stream()                      // returns a Stream<T>
                                 .mapToInt(Dish::getCalories) //returns a IntStream
                                 .sum();                        //uses the sum method. also has avg,count,max,etc methods
        logger.debug("the sum of IntStream is {}", totalCal);

            //can convert back to a object - Integer
        IntStream inStrm = dishes.stream().mapToInt(Dish::getCalories);
        Stream<Integer> objStrm = inStrm.boxed();

        /*sometimes you do not want to use the initial value. In case of max, getting a 0 as max is wrong.
        There are specialized Optional classes, like optionalInt
         */
        OptionalInt maxint = dishes.stream()
                                    .mapToInt(Dish::getCalories)
                                    .max();
        int maxcal = maxint.orElse(1); //here you can specify what max value you want using the orElse method of optional
        logger.debug("the max value of calories using OptionalInt is {}", maxcal);


        /* Generating number ranges
        One common use case is to generate number ranges.This can be done using the static methods of IntStream,
        LogStream and other primitive specialized classes.
         */
        //rangeClosed means the last digit is inclusive. Range can be exclusive also
        IntStream evenno = IntStream.rangeClosed(1,100).filter((e->e%2==0));
        logger.debug("the total even numbers from IntStream Range are {}", evenno.count());

        //Generating tuples
        /* This feature of IntStream ranges can be used to generate tuples
        One example is to find tuples of pythagoreos theorem
         */
        /* below flatmap is required or else you will get Stream of Stream
        mapToObj also converts the IntStream to an Object stream as we return an array instead of a stream of Ints.
         */
         Stream<int[]> pyTriplets = IntStream.rangeClosed(1,100)
                                    .boxed()        //required as intStream only returns an int and you need obj for map
                                    .flatMap(a -> IntStream.rangeClosed(a,100)
                                                            .filter(b-> Math.sqrt(a*a+b*b)%1==0)
                                                            .mapToObj(b-> new int[]{a,b,(int)Math.sqrt(a*a+b*b)})); //can do a boxed and map but mapToObj is a single method with the same effect

            pyTriplets.limit(5).forEach(t->System.out.println(t[0]+" "+t[1]+" "+ t[2]));

        // Different ways of creating Streams

        //Stream from some explicit values using Stream.of method

            Stream<String> strexpl = Stream.of("world", "of", "examples");
            strexpl.map(String::toUpperCase).forEach(System.out::println);
        //empty stream
            Stream<String> emp = Stream.empty();

        //stream from arrays
            int[] intarr = {2, 3,5, 7, 9, 11, 13};
            IntStream exstrem = Arrays.stream(intarr);
            int intsum = exstrem.sum();
            logger.debug("the sum is {}", intsum);

        //stream from Files
        /*  Java NIO lib contains methods to generate streams from files
        java.nio.file.Files has many methods for generating streams
        In the example below, a stream of lines is created, one line is a string
         */
        long uniquewords = 0;
        try(Stream<String> lines = Files.lines(Paths.get("/home/avdhut/workspace/projects/java8ex/pom.xml"), Charset.defaultCharset())){
            uniquewords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                                .distinct()
                                .count();
        }
        catch(IOException ex){
            System.out.println(ex);
        }
        logger.debug("unique words in file is {}", uniquewords);


        /* Infinite streams
        One can create infinte streams using 'iterate' and 'generate' methods
        You normally restrict the streams using the limit method
        The below example generates a tuple of fibonacci series and limits it to only 20
         */

        Stream.iterate(new int[]{0,1}, t->new int[]{t[1], t[0]+t[1]}) //iterate takes a UnaryOperator to generate the infinite stream
              .limit(20)
                .forEach(t->System.out.println("( " + t[0] + "," + t[1] + " )"));

        //variation that produces the series instead of tuples
        Stream.iterate(new int[]{0,1}, t->new int[]{t[1], t[0]+t[1]})
                .limit(20)
                .map(t->t[0])
                .forEach(System.out::println);

        /* Generate method
        Unlike iterate method which operates the function on successfully generated values,
        Generate method generates a new value each time. It takes a Supplier.
        This it is stateless like iterate
        However you can define a Supplier function that stores state and apply function repeatedly.
         But this is not recommended as a stateful function is not good for parallelism/parallel stream
         */
        Stream.generate(Math::random)
                 .limit(5)
                 .forEach(System.out::println);

        /* Specialized streams like IntStream, etc also has similar methods
        You can define a IntSupplier to generate the fibonacci series but not advised
         because of the stateful nature
         */
        IntSupplier fsup = new IntSupplier() {

            private int previous =0; //holds state
            private int current = 1;
            @Override
            public int getAsInt() {
                int oldprev = this.previous;
                int nextval = this.previous+this.current;
                this.previous=this.current;
                this.current=nextval;
                return oldprev;
            }
        };

        IntStream.generate(fsup)  //takes a supplier
                .limit(5)
                .forEach(System.out::println);


    }

    static List<Dish> getDishes() {

        return Arrays.asList(
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("kingfish", false, 300, Dish.Type.FISH),
                new Dish("biryani", true, 200, Dish.Type.VEG),
                new Dish("pork", false, 500, Dish.Type.MEAT),
                new Dish("salmon", false, 250, Dish.Type.FISH),
                new Dish("salad", true, 150, Dish.Type.VEG),
                new Dish("mutton", false, 600, Dish.Type.MEAT),
                new Dish("prawn", false, 175, Dish.Type.FISH),
                new Dish("rice", true, 100, Dish.Type.VEG)
                );

    }
}
