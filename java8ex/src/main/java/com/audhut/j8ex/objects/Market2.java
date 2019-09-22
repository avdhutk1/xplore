package com.audhut.j8ex.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by avdhut on 23/9/17.
 * A class to test different ways of method references introduced in java8
 */
public class Market2 {

    public <T> List<T> getGreenColoredApples(List<T> apples, Predicate<T> colorTest){

        List<T> selectedApples = new ArrayList<>();

        for (T a : apples){
            //here the predicate method only returns a boolean
            if (colorTest.test(a)){
                selectedApples.add(a);
            }
        }

        return selectedApples;
    }

    public <T> T createApple(Supplier<T> appleCrate) {

            //here the function method executes the get method which processes and supplies the output
            return appleCrate.get();
    }

    public <T> List<T> createCrateOfApples(List<Integer> weights, Function<Integer,T> appleCrate){

        List<T> apples = new ArrayList<>();

        for (Integer a : weights) {
            //here the function method executes the apply method which processes and supplies the output
            apples.add(appleCrate.apply(a));
        }

        return apples;
    }

    public <T> List<T> createColorCrateOfApples(List<String> colors, List<Integer> weights, BiFunction<String,Integer,T> appleCrate){

        List<T> apples = new ArrayList<>();

        for (Integer a : weights) {
            for(String col : colors){
                //here the function method executes the apply method which processes and supplies the output
                apples.add(appleCrate.apply(col, a));
            }
        }

        return apples;
    }

}
