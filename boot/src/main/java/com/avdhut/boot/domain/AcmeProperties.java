package com.avdhut.boot.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.Inet4Address;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Demonstrates the use of @ConfigurationProperties to bind properties from application.yml
 * to a bean
 * In the below example, the bean is bound to properties defined with the top level prefix called
 * "acme". Check the application.yml
 * It also has nested bean called security which also has bind variables
 * It shows how different types of properties can be injected:
 * 1. Simple scalar values like String
 * 2. How Spring forces conversion to proper type - eg. Inet4Address for remoteAddress
 * 3. List of complex Object types - list<CustomerType>. Check the CustomerType bean and application.yml
 * 4. A nested bean - security. This in turn has nested lists and maps
 * 5. Map of complex Object type - map<String, DeviceType> Check the DeviceType bean and application.yml
 *
 * Also note that the field names should match the name of property in application.yml
 * but with relaxed binding support.
 * Camelcase is matched exactly with the field name. eg. enabled
 * kebab style property, eg. remote-address is matched to camel case remoteAddress below. similarly customer-type
 * similarly for other properties also
 *
 * Also not required is @Autowired and initialization of list/map/objects as long as the properties is in the
 * hierarchy of properties under acme. See security example where autowired is commented
 * also see bean Security where list and map is not initialized
 * All you need is @Component in the nested bean
 *
 * You can also use java validation annotation like @Notnull, etc.
 * In case of nested bean, the main bean should have @Valid annotation and properties should have jsr-303 annotation
 * refer to security bean below and its properties that have the @NotNull annotation
 */

@Component
@ConfigurationProperties("acme")
public class AcmeProperties {

    private String enabled;
    @NotNull
    private Inet4Address remoteAddress;
    private List<CustomerType> customerTypes;
    private Map<String, DeviceType> deviceTypes;

    //@Autowired
    @Valid
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

    public void setSecurity(Security security) {
        this.security = security;
    }

    public List<CustomerType> getCustomerTypes() {
        return customerTypes;
    }

    public void setCustomerTypes(List<CustomerType> customerTypes) {
        this.customerTypes = customerTypes;
    }

    public Map<String, DeviceType> getDeviceTypes() {
        return deviceTypes;
    }

    public void setDeviceTypes(Map<String, DeviceType> deviceTypes) {
        this.deviceTypes = deviceTypes;
    }
}
