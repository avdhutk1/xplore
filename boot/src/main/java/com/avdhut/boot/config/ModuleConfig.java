package com.avdhut.boot.config;

import com.avdhut.boot.service.ModuleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Demonstrates how additional config classes can be used in boot application
 * It creates a module service bean that is injected into the greeting controller
 */

@Configuration
public class ModuleConfig {

    @Bean
    public ModuleService moduleService(){
      System.out.println("=========> module service is being created");
      return new ModuleService();
    }
}