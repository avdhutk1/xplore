package com.avdhut.boot.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Demonstrates binding of variables from application.yml to a nested bean using @ConfigurationProperties
 * In the application.yml, the prefix is "acme.security"
 * It also demonstrates mapping to list and map from the prop file
 * For maps, spring removes any special characters like '-' or other characters other than lower case
 * in the key.This is to support relaxed binding
 * But if you want to retain the characters, you need to surround the key with []
 * for eg. [/key1]: value1.
 * The example below contains two such keys - refer the application.yml
 * Also you do not need to initialize list and map. see below example where initialization is commented out
 * All you need is @Component annotation
 */

@Component
//@ConfigurationProperties("acme.security")
public class Security {

    @NotEmpty
    private String username;
    private String password;
    //private List<String> roles = new ArrayList<>(Collections.emptyList());
    //private Map<String, String> ssl = new HashMap<>(Collections.emptyMap());
    private List<String> roles;
    private Map<String, String> ssl;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Map<String, String> getSsl() {
        return ssl;
    }

    public void setSsl(Map<String, String> ssl) {
        this.ssl = ssl;
    }
}
