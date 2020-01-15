package com.avdhut.boot.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.util.Collections;
import java.util.List;

/**
 * Demonstrates the use of @ConfigurationProperties to bind properties from application.yml
 * to a bean
 * In the below example, the bean is bound to properties defined with the top level prefix called
 * "acme". Check the application.yml
 * It also has nested bean called security which also has bind variables
 * It shows how different types of properties can be injected:
 * 1. Simple scalar values like String
 * 2. How Spring forces conversion to proper type - eg. Inet4Address for remoteAddress
 * 3. List of Object types - list<CustomerType>. Check the CustomerType beanand applicaiton.yml
 * 4. A nested bean - security. This in turn has nested lists and maps
 * Also note that the field names should match the name of property in application.yml
 * but with relaxed binding support.
 * Camelcase is matched exactly with the field name. eg. enabled
 * kebab style property, eg. remote-address is matched to camel case remoteAddress below. similarly customer-type
 * similalry for other properties also
 */

@Component
@ConfigurationProperties("acme")
public class AcmeProperties {

    private String enabled;
    private Inet4Address remoteAddress;
    private List<CustomerType> customerTypes;

    @Autowired
    private Security security;

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public Inet4Address getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(Inet4Address remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public Security getSecurity() {
        return security;
    }

    public List<CustomerType> getCustomerTypes() {
        return customerTypes;
    }

    public void setCustomerTypes(List<CustomerType> customerTypes) {
        this.customerTypes = customerTypes;
    }
}
