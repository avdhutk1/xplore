package com.audhut.spring5ex.impl;

import com.audhut.spring5ex.intf.CompactDisc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//comment out @Component if you want the AppConfig class to instantiate the bean
//if you want component scan to create bean, the constructor params need to be autowired/injected
@Component
//this is an example of injecting properties in the class and using placeholders
@PropertySource("classpath:app.properties")
public class BlankDisc implements CompactDisc {

    private String title;
    private Integer size;

    //here the config class converts the env properties to a class (Integer) and injects it. See AppConfig class
    /*public BlankDisc(String title, Integer size){
        this.title = title;
        this.size= size;

    }*/

    //here the properties defined in the file are injected in the placeholders
    public BlankDisc(@Value("${disc.title}") String title, @Value("${disc.size}") Integer size){
        this.title = title;
        this.size= size;

    }

    @Override
    public void play() {
        System.out.println("the title is ==>" + title + " and size is ==>" + size);

    }
}
