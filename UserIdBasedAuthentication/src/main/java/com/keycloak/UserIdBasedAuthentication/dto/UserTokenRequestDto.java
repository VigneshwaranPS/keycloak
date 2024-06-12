package com.keycloak.UserIdBasedAuthentication.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserTokenRequestDto {

    private String locationId;
    private String devicePin;
}
