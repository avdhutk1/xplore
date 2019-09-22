package com.audhut.j8ex.intf;

/**
 * Created by avdhut on 18/2/18.
 */
@FunctionalInterface
public interface ValidationStrategy {

    public boolean validate(String str);
}
