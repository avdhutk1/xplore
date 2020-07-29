package com.avdhut.boot.service;

import com.avdhut.boot.config.AppConfig;
import com.avdhut.boot.domain.AcmeProperties;
import com.avdhut.boot.domain.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * This class demonstrates a service implementation of REST
 * It also includes examples on how external configs are injected
 */

@Component
public class GreetingServiceImpl implements GreetingService {

    private static final String template = "Hello %s";
    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(GreetingServiceImpl.class);

    //injects a bean where all external config properties are loaded and stored
    @Autowired
    AppConfig appconfig;

    //demonstrates how a property from yml file can be injected directly
    @Value("${interfaces.inventory.name}")
    String invIntfName;

    /*demonstrates how a commandLine arg can be injected.Requires to run jar command with --server.ip=<some value>
    Also demonstrates how a json can be used to inject properties values
    In command line use --spring.application.json={"server":{"ip":"192.168.145.78"}} to specify a json in command line
    comment it out to test with command line option
    It also demonstrates how a default value can be set if env variable is not specified
    The syntax ${var-name:default} is used below to set the default value
     */
    @Value("${server.ip:23.23.23.23}")
    String serverIp;

    /**
     * Demonstrates how @ConfigurationProperties is used to inject values from application.yml into a bean
     * Here the config values from application.yml is injected into a bean AcmeProperties
     * Refer to that bean for more details
     * It demonstrates how a nested class, list and map values are mapped into bean properties. It also includes how a complex type
     * is injected. A list and map of complex types is injected
     * Refer to CustomerType, DeviceType bean for example
     * Also it demonstrates how spring does type cast to an object. Refer 'remote-address' property that is typecast from a String
     * to a InetAddress Type
     */
    @Autowired
    AcmeProperties acmeprops;


    @Override
    public Greeting greet(String name) {
        System.out.println("AppConfigs values being printed ===> " + appconfig.getInvInterfaceName());
        System.out.println("AppConfigs values being printed ===> " + appconfig.getInvInterfaceUrl());
        System.out.println("AppConfigs values being printed ===> " + appconfig.getOrdInterfaceName());
        System.out.println("AppConfigs values being printed ===> " + appconfig.getOrdInterfaceUrl());
        System.out.println("AppConfigs values being printed - list values from yml ===> " + appconfig.getOrdServer());
        System.out.println("AppConfigs values being printed - list values from yml ===> " + appconfig.getInvServer());
        System.out.println("AppConfigs values being printed  - direct injection into service ===> " + invIntfName);
        System.out.println("Command line values being printed  ===> " + serverIp);

        System.out.println("Acme properties using @ConfigurationProperties ============>");
        System.out.println(String.format("Acme enabled prop value is %s",acmeprops.getEnabled()));
        System.out.println(String.format("Acme remote address prop value is %s",acmeprops.getRemoteAddress().getHostAddress()));
        System.out.println(String.format("Acme security prop value is %s",acmeprops.getSecurity()));
        System.out.println(String.format("Acme security username prop value is %s",acmeprops.getSecurity().getUsername()));
        System.out.println(String.format("Acme security password prop value is %s",acmeprops.getSecurity().getPassword()));
        System.out.println(String.format("Acme security roles-list prop value is %s",acmeprops.getSecurity().getRoles().toString()));
        System.out.println(String.format("Acme security sslprop-map prop value is %s",acmeprops.getSecurity().getSsl().toString()));
        System.out.println(String.format("Acme complex object list prop value is %s",acmeprops.getCustomerTypes().toString()));
        System.out.println(String.format("Acme complex object map prop value is %s",acmeprops.getDeviceTypes().toString()));

        logger.info("AppConfigs values being printed using logger ===> {}, {}", appconfig.getInvInterfaceName(),appconfig.getOrdInterfaceName());


        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
