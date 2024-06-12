package com.keycloak.UserIdBasedAuthentication.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StaffDTO {

    private String id;
    private String merchantId;
    private String authUserId;
    private String customId;
    private String fullName;
    private String email;
    private String mobilePhone;
    private Integer statusId;
    private Boolean isOwner;
    private String attributes;
    private Date createdTime;
    private Date modifiedTime;
    private String accessToken;

}
