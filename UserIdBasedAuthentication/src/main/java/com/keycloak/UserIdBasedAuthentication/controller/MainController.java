package com.keycloak.UserIdBasedAuthentication.controller;


import com.keycloak.UserIdBasedAuthentication.customException.AdminTokenException;
import com.keycloak.UserIdBasedAuthentication.customException.UserNotFoundException;
import com.keycloak.UserIdBasedAuthentication.service.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private KeycloakService keycloakService;

    @GetMapping("/{Uid}")
    public String getToken(@PathVariable("Uid") String userId) throws AdminTokenException, UserNotFoundException {
        return keycloakService.useKeycloakIdentity(userId);
    }
}
