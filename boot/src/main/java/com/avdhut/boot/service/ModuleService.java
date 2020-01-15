package com.avdhut.boot.service;

/**
 * This class demonstrates how a java config class is used to inject this class
 * It does not have a @component annotation but has a @bean method in the java config class
 * This bean is injected into the GreetingController class using @Autowired
 */
public class ModuleService {

    public String getMessage(){
        return "module service is operational";
    }
}
