package com.audhut.j8ex;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

/**
 * Created by avdhut on 14/1/18.
 *
 * This is an example of a custom collector
 * Here the finding of primes is optimized. Instead of diving the candidate by all numbers in the list
 * it is divided by only prime numbers. The takeWhile method returns all the primes that are greater than the sqroot of the candidate
 * The isPrime method returns the prime numbers
 */
public class PrimeNumbersCollector implements
        Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean,List<Integer>>>{

    //supplies the empty accumulator where results are stored
    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> new HashMap<Boolean, List<Integer>>() {{
            put(true, new ArrayList<Integer>());
            put(true, new ArrayList<Integer>());

        }};
    }

    /*returns the reduction logic
    The first 'get' gets the prime list from the input and the second 'get' adds the candidate to the list
     */
    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (Map<Boolean,List<Integer>> acc, Integer candidate) ->
                        {
                            List<Integer> lt = acc.get(isPrime(acc.get(true), candidate));
                            if (lt!=null)
                                lt.add(candidate);
                        };
    }

    //not required but in parralization helps to merge two lists
    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (Map<Boolean, List<Integer>> map1, Map<Boolean, List<Integer>> map2) ->
        {
            map1.get(true).addAll(map2.get(true));
            map1.get(false).addAll(map2.get(false));
            return map1;
        };
    }

    //function does not have to do any transformation, it just returns the same type, i.e. list
    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH));
    }

    public <A> List<A> takeWhile(List<A> list, Predicate<A> p){
        int i=0;
        for (A item : list){
            if (!p.test(item)){
                return list.subList(0,i);
            }
            i++;
        }
        return list;
    }

    public boolean isPrime(List<Integer> primes, int candidate){
        int candidateRoot = (int) Math.sqrt((double)candidate);
        return takeWhile(primes, i->i<=candidateRoot).stream()
                .noneMatch(p->candidate%p==0);
    }
}
