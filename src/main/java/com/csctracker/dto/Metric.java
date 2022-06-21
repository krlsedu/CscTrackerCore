package com.csctracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Metric {
    private String appName;
    private String clazz;
    private String method;
    private String fullClassName;
    private Date date;
    private Long executionTime;
}
