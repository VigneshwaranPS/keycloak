package com.keycloak.UserIdBasedAuthentication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "mh_location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;
    @Column(length=36)
    private String merchantId;  //location id uuid
    @Column(length = 36)
    private String restaurantName;
    @Column(length = 36)
    private String name;
    @Column(length=20)
    private String phone;
    @Column(length=60)
    @Email
    private String email;
    @Column(length=128)
    private String addressLine1;
    @Column(length=128)
    private String addressLine2;
    @Column(length=128)
    private String addressLine3;
    @Column(length=128)
    private String city;
    @Column(length=10)
    private String state;
    @Column(length=10)
    private String pinCode;
    @Column(length=20)
    private String country;
    @Column(columnDefinition = "JSON")
    private String attributes;

}
