package com.audhut.spring5ex.impl;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by avdhut on 23/1/19.
 */
@Component
public class Musician {

    public static String DEFAULT_NAME = "Elton";

    private String name;

    //uncomment below for jsr250 lifecycle method execution
    //similarly you have @PreDestroy annotation to invoke before application shutdown
    //@PostConstruct
    private void init(){  //keep this private as it is only called once and not externally. Spring can call private
        if (null == name){
            name = DEFAULT_NAME;
        }
    }
/*
Similar to above, there is a @PreDestroy annotation to clear resources before bean destruction
 */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
