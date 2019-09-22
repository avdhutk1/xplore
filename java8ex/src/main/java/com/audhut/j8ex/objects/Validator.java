package com.audhut.j8ex.objects;

import com.audhut.j8ex.intf.ValidationStrategy;

/**
 * Created by avdhut on 18/2/18.
 */
public class Validator {
    private final ValidationStrategy stg;

    public Validator(ValidationStrategy stragy){
        this.stg = stragy;
    }

    public boolean validate(String str){
        return stg.validate(str);
    }

}
