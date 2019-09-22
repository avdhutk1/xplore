package com.audhut.spring5ex.impl;

/**
 * Created by avdhut on 1/2/19.
 */

/**
 * Used to demonstrate static factory method in Spring. Not thread safe
 */
public class OrderProcessor {

    private static OrderProcessor or = new OrderProcessor();

    public static OrderProcessor getInstance(){
        return or;
    }

    public String toString(){
        return new String("this is a order processor instance ");
    }

}
