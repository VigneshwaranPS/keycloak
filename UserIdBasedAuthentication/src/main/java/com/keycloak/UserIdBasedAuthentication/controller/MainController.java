package com.keycloak.UserIdBasedAuthentication.controller;


import com.keycloak.UserIdBasedAuthentication.service.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private KeycloakService keycloakService;


    @GetMapping
    public String getToken(){
        return keycloakService.useKeycloakIdentity();
    }

}
