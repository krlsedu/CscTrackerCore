package com.csctracker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Configs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String favoriteContact;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String applicationNotify;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String timeZone;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String configs;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
