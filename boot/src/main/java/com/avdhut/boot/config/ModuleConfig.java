package com.avdhut.boot.config;

import com.avdhut.boot.filter.CreationAuditFilter;
import com.avdhut.boot.service.ModuleService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Demonstrates how additional config classes can be used in boot application
 * It creates a module service bean that is injected into the greeting controller
 *
 * Also demonstrates how a filter can be configured using Java config
 * Usually can be used to filter specific URL's
 * The FilterRegistrationBean method below registers the filter
 */

@Configuration
public class ModuleConfig {

    @Bean
    public ModuleService moduleService(){
      System.out.println("=========> module service is being created");
      return new ModuleService();
    }

    /**
     * Registers the filter. This example registers it for a specific URL
     * Also sets the order of execution
     */
    @Bean
    public FilterRegistrationBean<CreationAuditFilter>  createAuditFilter(){

        FilterRegistrationBean<CreationAuditFilter> cbf = new FilterRegistrationBean<>();

        cbf.setFilter(new CreationAuditFilter());
        cbf.addUrlPatterns("/services/order/product/*");
        cbf.setOrder(3);

        return cbf;
    }
}