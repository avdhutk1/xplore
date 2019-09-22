package com.audhut.spring5ex.impl;

import com.audhut.spring5ex.intf.Dessert;
import org.springframework.stereotype.Component;


@Component
public class Cake implements Dessert {

    @Override
    public String serve() {
        return "cake served";
    }
}
