package com.audhut.spring5ex.impl;

import com.audhut.spring5ex.annotation.Cold;
import com.audhut.spring5ex.annotation.Creamy;
import com.audhut.spring5ex.annotation.Fruity;
import com.audhut.spring5ex.intf.Dessert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * This class demonstrates how autowiring is used.
 * This class injects a bean using Quaifier as there are multiple implementations
 * commment out @Qualifier to use qualifier annotation
 * It also uses multiple custom annotations to inject different implementation
 * comment out @Creamy or @Fruity to see different implementation being injected
 * @Cold is a common annotation in both implementations
 */
@Component
public class Dinner {

    private Dessert dessert;

    private Dessert getDessert() {
        return dessert;
    }

    @Autowired
    //@Qualifier("cold")
    @Cold
    //@Creamy
    @Fruity
    private void setDessert(Dessert dessert) {
        this.dessert = dessert;
    }

    public void serveDessert(){
        System.out.println(" Dessert served is --> " + this.dessert.serve());
    }

}
