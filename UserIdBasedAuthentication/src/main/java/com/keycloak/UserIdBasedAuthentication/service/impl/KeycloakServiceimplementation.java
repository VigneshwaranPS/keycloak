package com.keycloak.UserIdBasedAuthentication.service.impl;

import com.keycloak.UserIdBasedAuthentication.customException.AdminTokenException;
import com.keycloak.UserIdBasedAuthentication.customException.UserNotFoundException;
import com.keycloak.UserIdBasedAuthentication.service.KeycloakService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.*;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.netty.http.client.HttpClient;

@Service
public class KeycloakServiceimplementation implements KeycloakService{

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

    public ResponseEntity<AccessTokenResponse> getAdminToken() throws AdminTokenException {
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

        ResponseEntity<AccessTokenResponse> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, AccessTokenResponse.class);
            return response;
        } catch (Exception e) {
            throw new AdminTokenException("Unauthorized user");
        }
    }


    public String callImpersonationAPI(String userId) throws AdminTokenException, UserNotFoundException {
        ResponseEntity<AccessTokenResponse> tokenResponse = getAdminToken();

        if (tokenResponse != null && tokenResponse.getStatusCode() == HttpStatus.OK) {
            AccessTokenResponse body = tokenResponse.getBody();
            if (body == null || body.getToken() == null) {
                throw new AdminTokenException("Admin Token is Empty");
            }

            String accessToken = body.getToken() ;
            logger.info("Admin Token: {}", accessToken);
            String impersonationApiUrl = String.format("http://localhost:8081/admin/realms/DemoRealm/users/%s/impersonation", userId);

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();

            try {
                ResponseEntity<String> impersonationResponse = restTemplate.exchange(impersonationApiUrl, HttpMethod.POST, requestEntity, String.class);
                if (impersonationResponse.getStatusCode() == HttpStatus.OK) {
                    HttpHeaders responseHeaders = impersonationResponse.getHeaders();
                    List<String> cookies = responseHeaders.get(HttpHeaders.SET_COOKIE);

                    if (cookies != null && !cookies.isEmpty()) {
                        String str = cookies.get(0);
                        logger.info("Exchange Token: {}", str);
                        return str.substring(str.indexOf('=') + 1, str.indexOf(';'));
                    } else {
                        throw new UserNotFoundException("No cookies found in the response.");
                    }
                } else {
                    throw new UserNotFoundException("Failed to impersonate user, status code: " + impersonationResponse.getStatusCode());
                }
            } catch (HttpClientErrorException e) {
                throw new UserNotFoundException("Client error during impersonation: " + e.getStatusCode() + " - " + e.getStatusText(), e);
            } catch (HttpServerErrorException e) {
                throw new UserNotFoundException("Server error during impersonation: " + e.getStatusCode() + " - " + e.getStatusText(), e);
            } catch (ResourceAccessException e) {
                throw new UserNotFoundException("Resource access error during impersonation: " + e.getMessage(), e);
            } catch (RestClientException e) {
                throw new UserNotFoundException("Error during impersonation: " + e.getMessage(), e);
            }
        } else {
            throw new AdminTokenException("Failed to retrieve admin token.");
        }
    }

    public String useKeycloakIdentity(String userId) throws AdminTokenException, UserNotFoundException {
        String keycloakIdentity = callImpersonationAPI(userId);
        if (keycloakIdentity == null || keycloakIdentity.isEmpty()) {
                logger.error("Failed to retrieve keycloak identity token.");
            return null;
        }

        String apiUrl = "http://localhost:8081/realms/DemoRealm/protocol/openid-connect/auth";
        String parameter = "?response_type=token&client_id=Demo&response_mode=fragment&redirect_uri=http://localhost:8080";

        WebClient webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().followRedirect(false)
                ))
                .defaultHeader(HttpHeaders.COOKIE, "KEYCLOAK_IDENTITY=" + keycloakIdentity)
                .build();

        try {
            ClientResponse clientResponse = webClient.get()
                    .uri(parameter)
                    .exchange()
                    .block();

            if (clientResponse.statusCode().is3xxRedirection()) {
                String location = clientResponse.headers().asHttpHeaders().getLocation().toString();
                logger.info("Location Header: {}", location);
                URI uri = new URI(location);
                String fragment = uri.getFragment();
                if (fragment != null) {
                    String[] params = fragment.split("&");
                    for (String param : params) {
                        String[] keyValue = param.split("=");
                        if (keyValue.length == 2 && keyValue[0].equals("access_token")) {
                            return keyValue[1];
                        }
                    }
                }
            }
        } catch (WebClientResponseException | URISyntaxException e) {
            return e.getMessage();
        }
        return "Failed to Fetch User Token";
    }
}
