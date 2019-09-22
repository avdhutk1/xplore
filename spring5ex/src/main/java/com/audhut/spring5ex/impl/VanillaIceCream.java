package com.audhut.spring5ex.impl;


import com.audhut.spring5ex.annotation.Cold;
import com.audhut.spring5ex.annotation.Creamy;
import com.audhut.spring5ex.intf.Dessert;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * The @Primary annotation defines this bean as primary bean to be injected
 * comment it out to check the behaviour
 * Better way is to use @Qualifier annotation. Using @Component("beanId") is also same as Qualifier
 * comment out the @Qualifier annotation to use this
 * If two or more implementation are similar, then you can define custom annotations using @Qualifiers.
 * i.e define Custom qualifiers. This way you can also define multiple annotations
 * */

@Component
//@Primary
//@Qualifier("cold")
@Cold
@Creamy
public class VanillaIceCream implements Dessert {

    @Override
    public String serve() {
        return "Vanilla ice cream served";
    }
}
