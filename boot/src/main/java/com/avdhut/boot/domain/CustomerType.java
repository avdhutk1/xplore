package com.avdhut.boot.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Demonstrates how a list of complex types can be mapped from application.yml
 * Here fields should match the property in application.yml with relaxed binding
 * eg. customer-type in application.yml is mapped to CustomerType class
 */

@Component
@ConfigurationProperties("acme.customer-types")
public class CustomerType {

    private String type;
    private String Description;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "CustomerType{" +
                "type='" + type + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
