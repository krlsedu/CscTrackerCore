package com.csctracker.model;

import com.csctracker.enums.UserType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String requestId;

    @Enumerated(EnumType.STRING)
    private UserType type;
}
