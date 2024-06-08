package com.keycloak.UserIdBasedAuthentication.service;

import java.util.List;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
public class KeycloakService {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakService.class);

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.clientId}")
    private String clientId;

    @Value("${keycloak.clientSecret}")
    private String clientSecret;

    @Value("${keycloak.adminUsername}")
    private String adminUsername;

    @Value("${keycloak.adminPassword}")
    private String adminPassword;

    public ResponseEntity<AccessTokenResponse> getToken() {
        String url = "http://localhost:8081/realms/DemoRealm/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("grant_type", "password");
        requestBody.add("username", adminUsername);
        requestBody.add("password", adminPassword);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AccessTokenResponse> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, AccessTokenResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response;
        } else {
            return null;
        }
    }

    public String callImpersonationAPI() {
        ResponseEntity<AccessTokenResponse> tokenResponse = getToken();
        if (tokenResponse != null && tokenResponse.getStatusCode() == HttpStatus.OK) {
            String accessToken = tokenResponse.getBody().getToken();
            String impersonationApiUrl = "http://localhost:8081/admin/realms/DemoRealm/users/f5b60a78-4bac-43bf-87a3-4b49ff3b5a23/impersonation";

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken); // Set the access token in the Authorization header

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> impersonationResponse = restTemplate.exchange(impersonationApiUrl, HttpMethod.POST, requestEntity, String.class);

            if (impersonationResponse.getStatusCode() == HttpStatus.OK) {
                HttpHeaders responseHeaders = impersonationResponse.getHeaders();
                List<String> cookies = responseHeaders.get(HttpHeaders.SET_COOKIE);
                String str = cookies.get(0);
                System.out.println("str : "+str);
                return str.substring( str.indexOf('=')+1 , str.indexOf(';'));
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public String useKeycloakIdentity() {

        String keycloakIdentity = callImpersonationAPI();
        String apiUrl = "http://localhost:8081/realms/DemoRealm/protocol/openid-connect/auth";
        String params = "?response_type=token&client_id=Demo&response_mode=fragment&redirect_uri=http://localhost:8080";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(keycloakIdentity);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(apiUrl + params, HttpMethod.GET, requestEntity, String.class);
        HttpHeaders responseHeaders = response.getHeaders();

        logger.info("Header {} " , responseHeaders);
        logger.info("Header Location {} " , responseHeaders.getLocation());

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }


//    public List<CookieData> callImpersonationAPI() {
//
//        ResponseEntity<AccessTokenResponse> tokenResponse = getToken();
//
//        if (tokenResponse != null && tokenResponse.getStatusCode() == HttpStatus.OK) {
//
//            String impersonationApiUrl = "http://localhost:8081/admin/realms/DemoRealm/users/f5b60a78-4bac-43bf-87a3-4b49ff3b5a23/impersonation";
//
//            String accessToken = tokenResponse.getBody().getToken();
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setBearerAuth(accessToken);
//
//            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
//
//            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<String> impersonationResponse = restTemplate.exchange(impersonationApiUrl, HttpMethod.POST, requestEntity, String.class);
//
//            HttpHeaders responseHeaders = impersonationResponse.getHeaders();
//            List<String> cookieStrings = responseHeaders.get(HttpHeaders.SET_COOKIE);
//            List<CookieData> cookies = CookieParser.parseCookies(cookieStrings);
//            return cookies;
//        } else {
//            return null;
//        }
//    }
}
