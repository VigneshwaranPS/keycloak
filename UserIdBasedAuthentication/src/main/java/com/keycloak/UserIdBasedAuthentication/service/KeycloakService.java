package com.keycloak.UserIdBasedAuthentication.service;

import com.keycloak.UserIdBasedAuthentication.customException.AdminTokenException;
import com.keycloak.UserIdBasedAuthentication.customException.UserNotFoundException;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;


public interface KeycloakService {

    ResponseEntity<AccessTokenResponse> getAdminToken() throws AdminTokenException;

    String callImpersonationAPI(String userId) throws AdminTokenException, UserNotFoundException;

    String useKeycloakIdentity(String userId) throws AdminTokenException, UserNotFoundException;

}
