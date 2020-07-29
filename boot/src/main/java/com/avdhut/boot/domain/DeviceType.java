package com.avdhut.boot.domain;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Demonstrates how a map of complex types can be mapped from application.yml
 * Here fields should match the property in application.yml with relaxed binding
 * eg. device-types in application.yml is mapped to DeviceType class
 */

@Component
/*@ConfigurationProperties("acme.device-types")*/
public class DeviceType {

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "DeviceType{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
