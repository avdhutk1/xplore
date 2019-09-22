package com.audhut.spring5ex.impl;

import com.audhut.spring5ex.intf.MessageProvider;
import com.audhut.spring5ex.intf.MessageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by avdhut on 20/12/18.
 * @Service, @Repository, @Controller are specializations of @Component
 * You can use this for special injection like persistence xml, etc
 * Yuo can also use jsr annotation @Named("some-bean-name") instead of @Component
 */
@Service("renderer")
public class MessageRendererImpl implements MessageRenderer {

   /*can be used for Field injection
   avoid using field injection. cannot be used on final classes. Better to use property injection
   @Autowired can also be used to inject params in constructors or any other method
    */
    // @Autowired
    private MessageProvider mp;

    @Override
    public void render() {

        if (Objects.isNull(mp)){
            throw new RuntimeException("message rendered is not set");
        }

        System.out.println(mp.getMessage());

    }

    @Override
    @Autowired
    //you can also use @Inject annotation which is equivalent to Autowired
    //another jsr annotation called @Resource(name="xyz") can also be used. used to fine tune injection
    //you can also use @Qualifier("name-of-bean") with Autowired if there are multiple beans of same type
    public void setMessageProvider(MessageProvider msgp) {

        this.mp =msgp;
    }
}
