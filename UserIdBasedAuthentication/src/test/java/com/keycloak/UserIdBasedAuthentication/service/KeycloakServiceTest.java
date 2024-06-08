package com.keycloak.UserIdBasedAuthentication.service;

import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class KeycloakServiceTest {

    Logger logger = LoggerFactory.getLogger(KeycloakServiceTest.class);

    @Test
    public void getAdminToken() {
        String url = "http://localhost:8081/realms/DemoRealm/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", "Demo");
        requestBody.add("client_secret", "o3BC7QW6gJtC8LY9XQza5bkhnS6Y3BN8");
        requestBody.add("grant_type", "password");
        requestBody.add("username", "viki");
        requestBody.add("password", "123");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AccessTokenResponse> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, AccessTokenResponse.class);

        logger.info("Response {} " ,response.getBody().getToken());

    }


    @Test
    public void callImpersonationAPI() {
        String adminToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI1b1NBUm16ejljMlFjc2txa2hQcWdILUlxR0xMaHg3VDhsSzNid3pKT1o0In0.eyJleHAiOjE3MTc3ODYyMjIsImlhdCI6MTcxNzc4NTkyMiwianRpIjoiNGZkMzhlN2YtMmZhOS00OWZmLThhZTQtNTc4ZTUxOTIwMTVlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxL3JlYWxtcy9EZW1vUmVhbG0iLCJhdWQiOlsicmVhbG0tbWFuYWdlbWVudCIsImJyb2tlciIsImFjY291bnQiXSwic3ViIjoiMmQ1YWM5MWQtZWIxNC00ZTQwLTg2NGEtN2E5ZTNkNmM3ZDllIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiRGVtbyIsInNlc3Npb25fc3RhdGUiOiJlNjc5OTc5My1iZjdlLTQ2ZmQtYTRjYy0wMTVkN2Q4NTY1Y2IiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbIioiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1kZW1vcmVhbG0iLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InJlYWxtLW1hbmFnZW1lbnQiOnsicm9sZXMiOlsidmlldy1yZWFsbSIsInZpZXctaWRlbnRpdHktcHJvdmlkZXJzIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJyZWFsbS1hZG1pbiIsImNyZWF0ZS1jbGllbnQiLCJtYW5hZ2UtdXNlcnMiLCJxdWVyeS1yZWFsbXMiLCJ2aWV3LWF1dGhvcml6YXRpb24iLCJxdWVyeS1jbGllbnRzIiwicXVlcnktdXNlcnMiLCJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwidmlldy1ldmVudHMiLCJ2aWV3LXVzZXJzIiwidmlldy1jbGllbnRzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWdyb3VwcyJdfSwiRGVtbyI6eyJyb2xlcyI6WyJ1bWFfcHJvdGVjdGlvbiJdfSwiYnJva2VyIjp7InJvbGVzIjpbInJlYWQtdG9rZW4iXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJ2aWV3LWFwcGxpY2F0aW9ucyIsInZpZXctY29uc2VudCIsInZpZXctZ3JvdXBzIiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJkZWxldGUtYWNjb3VudCIsIm1hbmFnZS1jb25zZW50Iiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwic2lkIjoiZTY3OTk3OTMtYmY3ZS00NmZkLWE0Y2MtMDE1ZDdkODU2NWNiIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiJ2aWtpIHBzIiwicHJlZmVycmVkX3VzZXJuYW1lIjoidmlraSIsImdpdmVuX25hbWUiOiJ2aWtpIiwiZmFtaWx5X25hbWUiOiJwcyIsImVtYWlsIjoidmlraUBnbWFpbC5jb20ifQ.NG0KTU_Sa7-r_V680skrDBDN3sr61m3Wen9NEl5bp_U-52aR0yn4r6p6gBHLEHCAwwyw08NvRt8rznjLVZjuYXwkWaIO-gl_HST_AOLI5Q5IPepR-9DTfEG-k7uYiV_98iD9aySwBSVcXLxSmeqZhIcQuSRAc2J4DIDC7xtGJpyV8GHzVCOOXKXILxPWvROLfH-Q-f-Pt-BtHsVGBGcZ93N98rlga0fdAXoydyVyrV7pLIE5mMRNBm2pwa6KzUKLzPLyYYaZZFg-UKcjO0NM4WY6Ozm-aVBsJYex59Zt1ePhlp347StKIVV5Fy0aluDq-obuhFPNHiwNU_rPdzuwvg";
        if (adminToken != null) {
            String accessToken = adminToken;
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

                logger.info(str.substring( str.indexOf('=')+1 , str.indexOf(';')));
            }
        }
    }


    @Test
    public void useKeycloakIdentity() {

        String keycloakIdentity = "eyJhbGciOiJIUzUxMiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIxYjZhZWZlMi00MmQ2LTRiMmUtOGM0OC02NTRlYmNlOTQyZTgifQ.eyJleHAiOjE3MTc4MjE5NjMsImlhdCI6MTcxNzc4NTk2MywianRpIjoiYzNiZWQ1NDQtNTNiMy00YTM5LWE2MzUtMTBjNGYwMjQ5ODkwIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxL3JlYWxtcy9EZW1vUmVhbG0iLCJzdWIiOiJmNWI2MGE3OC00YmFjLTQzYmYtODdhMy00YjQ5ZmYzYjVhMjMiLCJ0eXAiOiJTZXJpYWxpemVkLUlEIiwic2Vzc2lvbl9zdGF0ZSI6IjVmZDM0NDY5LTg0MjgtNGNlNi04ODAyLTdkMzljOWQxNzkyOSIsInNpZCI6IjVmZDM0NDY5LTg0MjgtNGNlNi04ODAyLTdkMzljOWQxNzkyOSIsInN0YXRlX2NoZWNrZXIiOiJPVEx5Vm1faGx2UEM5Y1BYSDVGZUhFRGJlNmRVRUdFVnc1cDItSlBUQndFIn0.tW2IAvuNaHH1oxuefI8tB0V2PMdysPvyECl1n1wZ9SGJ2HKJ4QXrd6aaFo8FeIcFqOTERyO_309L72mJNli1uQ";
        String apiUrl = "http://localhost:8081/realms/DemoRealm/protocol/openid-connect/auth";
        String params = "?response_type=token&client_id=Demo&response_mode=fragment&redirect_uri=http://localhost:8080";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(keycloakIdentity);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(apiUrl + params, HttpMethod.GET, requestEntity, String.class);
        HttpHeaders responseHeaders = response.getHeaders();

        logger.info("Header {} " , responseHeaders);
        logger.info("Header {} " , responseHeaders.getLocation());

    }

}