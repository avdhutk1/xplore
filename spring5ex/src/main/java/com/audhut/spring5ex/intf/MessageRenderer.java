package com.audhut.spring5ex.intf;

/**
 * Created by avdhut on 19/12/18.
 */
public interface MessageRenderer {

    public void render();
    public void setMessageProvider(MessageProvider msgp);
}
