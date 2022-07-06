package com.csctracker.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInfo {
    @Value("${spring.application.name}")
    private String appName;
    @Value("${server.port}")
    private String serverPort;
    @Value("${service.host:htto://service}")
    private String serviceHost;
    @Value("${service.port:5000}")
    private String servicePort;

    public String getAppName() {
        return appName;
    }

    public String getServerPort() {
        return serverPort;
    }

    public String getServiceHost() {
        return serviceHost;
    }

    public String getServicePort() {
        return servicePort;
    }
}
