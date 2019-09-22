package com.audhut.spring5ex.impl;

import com.audhut.spring5ex.intf.MessageProvider;
import org.springframework.stereotype.Component;

/**
 * Created by avdhut on 20/12/18.
 * To enable Spring to find beans, you need to annotate with stereotype like Component, Service etc
 * This along with Autowired enables wiring of beans
 */
@Component("provider")
public class MessageProviderImpl implements MessageProvider {

    @Override
    public String getMessage() {
        return "Hello Audhut";
    }
}
