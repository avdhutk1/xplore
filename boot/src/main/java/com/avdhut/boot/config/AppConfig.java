package com.avdhut.boot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * This class demonstrates how external config values are injected
 * The values are injected from application.yml file
 * example below includes hierarchical string properties and also list properties
 */

@Component
public class AppConfig {

    @Value("${interfaces.ordering.url}")
    private String ordInterfaceUrl;

    @Value("${interfaces.inventory.url}")
    private String invInterfaceUrl;

    @Value("${interfaces.ordering.name}")
    private String ordInterfaceName;

    @Value("${interfaces.inventory.name}")
    private String invInterfaceName;

    //this injects list values from yml file
    @Value("${externalSystems.servers[0]}")
    private String ordServer;

    @Value("${externalSystems.servers[1]}")
    private String invServer;

    public String getOrdInterfaceUrl() {
        return ordInterfaceUrl;
    }

    public String getInvInterfaceUrl() {
        return invInterfaceUrl;
    }

    public String getOrdInterfaceName() {
        return ordInterfaceName;
    }

    public String getInvInterfaceName() {
        return invInterfaceName;
    }

    public String getOrdServer() {
        return ordServer;
    }

    public String getInvServer() {
        return invServer;
    }

    public void setOrdInterfaceUrl(String ordInterfaceUrl) {
        this.ordInterfaceUrl = ordInterfaceUrl;
    }

    public void setInvInterfaceUrl(String invInterfaceUrl) {
        this.invInterfaceUrl = invInterfaceUrl;
    }

    public void setOrdInterfaceName(String ordInterfaceName) {
        this.ordInterfaceName = ordInterfaceName;
    }

    public void setInvInterfaceName(String invInterfaceName) {
        this.invInterfaceName = invInterfaceName;
    }

    public void setOrdServer(String ordServer) {
        this.ordServer = ordServer;
    }

    public void setInvServer(String invServer) {
        this.invServer = invServer;
    }

}
