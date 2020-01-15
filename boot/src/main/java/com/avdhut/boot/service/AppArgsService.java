package com.avdhut.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

/**
 * This class demonstrates how application args supplied while executing a boot app
 * can be accesses in a bean
 * this bean has access to those app args via the argument ApplicationArguments injected in the constructor
 */
@Component
public class AppArgsService {


    @Autowired
    public AppArgsService(ApplicationArguments appargs){
        System.out.println("=======> applications args are in a component " + appargs.toString());
    }
}
