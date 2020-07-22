package com.happysolutions.erideappratingbackend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.happysolutions.erideappratingbackend.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Table(name = "myuser")
@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserType userType;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String name;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}
