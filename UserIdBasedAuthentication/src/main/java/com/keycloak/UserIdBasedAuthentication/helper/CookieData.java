package com.keycloak.UserIdBasedAuthentication.helper;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CookieData {

    private String name;
    private String value;
    private String path;
    private String domain;
    private Integer maxAge;
    private boolean secure;
    private boolean httpOnly;
    private String sameSite;

}
