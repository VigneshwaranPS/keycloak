package com.keycloak.UserIdBasedAuthentication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "mh_staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(length = 36)
    private String merchantId;
    @Column(length = 36)
    private String authUserId;
    @Column(length = 12)
    private String customId;
    @Column(length = 40)
    private String fullName;
    @Email
    @Column(length = 80)
    private String email;
    @Column(length = 15)
    private String mobilePhone;
    @Column(length = 6)
    private String devicePin;
    private Integer statusId;
    private Boolean isOwner;
    @Column(columnDefinition = "JSON")
    private String attributes;
    private Date createdTime;
    private Date modifiedTime;

}
