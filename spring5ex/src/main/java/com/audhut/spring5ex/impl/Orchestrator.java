package com.audhut.spring5ex.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by avdhut on 1/2/19.
 */
public class Orchestrator {

    private OrderProcessor or;

    public void processOrder(){
        System.out.println("processing order using " + or.toString());
    }


    public OrderProcessor getOr() {
        return or;
    }

    @Autowired
    public void setOr(OrderProcessor or) {
        this.or = or;
    }
}
