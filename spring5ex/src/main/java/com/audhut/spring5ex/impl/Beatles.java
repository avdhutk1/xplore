package com.audhut.spring5ex.impl;

import com.audhut.spring5ex.intf.CompactDisc;
import org.springframework.stereotype.Component;

@Component
public class Beatles implements CompactDisc {


    @Override
    public void play() {
        System.out.println("Beatles playing now with java app config");

    }
}
