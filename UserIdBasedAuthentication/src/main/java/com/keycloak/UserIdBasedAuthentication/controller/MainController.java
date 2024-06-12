package com.keycloak.UserIdBasedAuthentication.controller;


import com.keycloak.UserIdBasedAuthentication.customException.AdminTokenException;
import com.keycloak.UserIdBasedAuthentication.customException.UserNotFoundException;
import com.keycloak.UserIdBasedAuthentication.dto.StaffDTO;
import com.keycloak.UserIdBasedAuthentication.dto.UserTokenRequestDto;
import com.keycloak.UserIdBasedAuthentication.entity.Location;
import com.keycloak.UserIdBasedAuthentication.repository.LocationRepository;
import com.keycloak.UserIdBasedAuthentication.service.KeycloakService;
import com.keycloak.UserIdBasedAuthentication.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MainController {


    private KeycloakService keycloakService;
    private TokenService tokenService;


    @GetMapping("/{Uid}")
    public String getToken(@PathVariable("Uid") String userId) throws AdminTokenException, UserNotFoundException {
        return keycloakService.useKeycloakIdentity(userId);
    }


    @PostMapping("/")
    public ResponseEntity<StaffDTO> getUserToken(@RequestBody UserTokenRequestDto userTokenRequestDto) throws UserNotFoundException, AdminTokenException {
        return tokenService.getUserToken(userTokenRequestDto);
    }
}
