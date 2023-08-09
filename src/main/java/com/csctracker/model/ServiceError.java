package com.csctracker.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class ServiceError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String message;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String service;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String body;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String headers;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String parameters;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String url;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String method;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String response;

    private Date dateTime;

}
