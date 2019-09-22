package com.audhut.j8ex.objects;

/**
 * Created by avdhut on 19/11/17.
 */
public class Dish {

    private String name;
    private boolean isVeg;
    private int calories;
    private Type type;


    public Dish(String name, boolean isVeg, int calories, Dish.Type type){
        this.name = name;
        this.isVeg = isVeg;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVeg() {
        return isVeg;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    public enum Type{
        MEAT, FISH, VEG
    }

    public String toString(){
        return "name is " +  this.name + " Type is "+ this.type + " calories are "+ this.getCalories() +
                " isVeg is " + this.isVeg();

    }
    public enum CalorificLevel {
        DIET,
        NORMAL,
        FAT;

    }
}
