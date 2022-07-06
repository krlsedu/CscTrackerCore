package com.csctracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ServiceRegisterDTO {
    private String appName;
    private String serviceName;
    private String url;
    private String method;
    private Map<String, String> attributes;
}
