package com.keycloak.UserIdBasedAuthentication.service;

import com.keycloak.UserIdBasedAuthentication.customException.AdminTokenException;
import com.keycloak.UserIdBasedAuthentication.customException.UserNotFoundException;
import com.keycloak.UserIdBasedAuthentication.dto.StaffDTO;
import com.keycloak.UserIdBasedAuthentication.dto.UserTokenRequestDto;
import org.springframework.http.ResponseEntity;

public interface TokenService {
    ResponseEntity<StaffDTO> getUserToken(UserTokenRequestDto userTokenRequestDto) throws UserNotFoundException, AdminTokenException;
}
