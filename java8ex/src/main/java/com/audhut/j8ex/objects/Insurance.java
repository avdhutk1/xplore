package com.audhut.j8ex.objects;

/**
 * Created by avdhut on 8/7/18.
 */
public class Insurance {

    private String name;

    //because name is mandatory and return value is not optional
    public Insurance(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
