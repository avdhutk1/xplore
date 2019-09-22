package com.audhut.j8ex.objects;

import java.util.Optional;

/**
 * Created by avdhut on 8/7/18.
 */
public class Person {

    private Optional<Car> car;

    //the optional return value indicates that the return value is optional
    public Optional<Car> getCar() {
        return car;
    }

    public void setCar(Optional<Car> car) {
        this.car = car;
    }
}
