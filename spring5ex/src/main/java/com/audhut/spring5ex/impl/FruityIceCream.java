package com.audhut.spring5ex.impl;

import com.audhut.spring5ex.annotation.Cold;
import com.audhut.spring5ex.annotation.Creamy;
import com.audhut.spring5ex.annotation.Fruity;
import com.audhut.spring5ex.intf.Dessert;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * This example defines custom annotation for autowiring
 * Two annotations are defined, both need to be defined in the class that injects this bean
 */
@Component
@Cold
@Fruity
public class FruityIceCream implements Dessert {
    @Override
    public String serve() {
        return "fruity ice cream served";
    }
}
