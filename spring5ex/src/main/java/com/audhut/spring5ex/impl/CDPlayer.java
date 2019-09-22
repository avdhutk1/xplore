package com.audhut.spring5ex.impl;

import com.audhut.spring5ex.intf.CompactDisc;
import com.audhut.spring5ex.intf.MusicPlayer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CDPlayer implements MusicPlayer {

    private CompactDisc cd;

    public CDPlayer(@Qualifier("beatles") CompactDisc cd){
        this.cd = cd;
    }

    @Override
    public void play() {
        cd.play();

    }
}
