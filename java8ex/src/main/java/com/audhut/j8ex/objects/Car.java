package com.audhut.j8ex.objects;

import java.util.Optional;

/**
 * Created by avdhut on 8/7/18.
 */
public class Car {

    //here the variable is defined as Optional
    private Optional<Insurance> insurance;

    //the optional return value indicates that the return value is optional
    public Optional<Insurance> getInsurance() {
        return insurance;
    }

    public void setInsurance(Optional<Insurance> insurance) {
        this.insurance = insurance;
    }
}
