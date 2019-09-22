package com.audhut.j8ex.objects;

/**
 * Created by avdhut on 30/7/17.
 */
public class Apple {
    private String color;
    private int weight;

    public Apple(int weight) {
        this.weight = weight;
    }

    public Apple(String color, int weight) {
        this.color = color;
        this.weight = weight;
    }

    public Apple() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isGreen(){
        return "green".equals(this.getColor());
    }

    public boolean isSameColor(Apple a){
        return this.getColor().equals(a.getColor());
    }

    public boolean isSameWeight(Apple a){
        return this.getWeight()== a.getWeight();
    }

    public boolean isHeavy(Apple a){
        return this.getWeight()>5;
    }

    @Override
    public String toString() {
        return "apple color: " + getColor() + " weight: " + getWeight();
    }
}
