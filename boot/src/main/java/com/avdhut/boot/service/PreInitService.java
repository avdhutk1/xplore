package com.avdhut.boot.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * This class is executed when the application starts
 * If you want to initialize application on startup this is the best way to do it
 * The class implements either ApplicationRunner or CommandLineRunner
 * The class implements both but only one is required, preferably ApplicationRunner
 *
 * If several classes implement the ApplicationRunner interface, you can specify the
 * order by using the @Order annotation
 */

@Component
public class PreInitService  implements ApplicationRunner, CommandLineRunner {

    //this is overriden from ApplicationRunnner
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("===>preinit service executed in Application runner");
    }

    //this is overriden from CommandLineRunner
    @Override
    public void run(String... args) throws Exception {
        System.out.println("===>preinit service executed in CommandLine runner");
    }
}
