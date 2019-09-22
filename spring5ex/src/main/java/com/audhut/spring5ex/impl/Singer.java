package com.audhut.spring5ex.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by avdhut on 1/1/19.
 * This class creates a new Singer object everytime it is called
 * By default, spring creates singleton objects. Using @prototype annotation, you can create a non-singleton
 * There are other scope types
 * @request - used for webmvc where a new object is created for every request
 * @session - beans with session scope will be created for every http session
 * @global session, @thread - not used regularly
 * @Custom - custom scope can be created by implementing Scope interface and using CustomScopeConfigurer
 *
 * You can define prototype either as a string or a better way is to use the ConfigurableBeanFactory static variables as shown below
 */
@Component("non-singleton")
//@Scope("prototype")
//better way compared to using string as above
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Singer {

    private String name = "unknown";

    public Singer(@Value("Elton John") String name){
        this.name =name;
    }
}
