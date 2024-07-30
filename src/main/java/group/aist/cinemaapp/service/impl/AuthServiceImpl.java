package group.aist.cinemaapp.service.impl;

import group.aist.cinemaapp.dto.request.UserLoginRequest;
import group.aist.cinemaapp.dto.request.UserRegisterRequest;
import group.aist.cinemaapp.dto.response.TokenResponse;
import group.aist.cinemaapp.service.AuthService;
import group.aist.cinemaapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RestTemplate restTemplate;
    private final UserService userService;

    @Value("${keycloak.base-url}")
    private String keycloakBaseUrl;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.client-secret}")
    private String clientSecret;

    @Override
    public String getAccessToken() {
        String tokenUrl = keycloakBaseUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        MultiValueMap<String, String> body = createBody("client_credentials");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, requestEntity, Map.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, String> responseBody = response.getBody();
                if (responseBody != null) {
                    var accessToken = responseBody.get("access_token");
                    return accessToken;
                }
            }
             throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Failed to obtain access token.");
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, "Failed to obtain access token.");
        }
    }

    @Override
    public void createUser(UserRegisterRequest request) {
        String createUserUrl = keycloakBaseUrl + "/admin/realms/" + realm + "/users";
        String accessToken = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        Map<String, Object> userPayload = createUserPayload(request);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(userPayload, headers);

        try {
            ResponseEntity<Void> response = restTemplate.postForEntity(createUserUrl, requestEntity, Void.class);
            if(response.getStatusCode() == HttpStatus.CREATED){
                String locationHeader = response.getHeaders().getLocation().toString();
                String userId = extractUserIdFromLocation(locationHeader);
                userService.AddKeycloakUserToDB(userId, request);
            }
        } catch (HttpClientErrorException e) {
            if(e.getStatusCode() == HttpStatus.CONFLICT) {
                throw new ResponseStatusException(BAD_REQUEST,"User already exists with username or email");
            } else {
                throw new ResponseStatusException(BAD_REQUEST,
                        String.format("Exception occurred while creating user: %s", e.getMessage()), e);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR,
                    "Exception occurred while creating user.", e);
        }
    }

    @Override
    public TokenResponse getUserAccessToken(UserLoginRequest request) {
        String tokenUrl = keycloakBaseUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        MultiValueMap<String, String> body = createBody("password");
        body.add("username", request.getUsername());
        body.add("password", request.getPassword());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, requestEntity, Map.class);
            log.info(response.getStatusCode().toString());
            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, String> responseBody = response.getBody();
                if (responseBody != null) {
                    return new TokenResponse(responseBody.get("access_token"), responseBody.get("refresh_token"));
                }
            }
            log.error("Failed to obtain access token. Status code: " + response.getStatusCode());
        } catch (Exception e) {
            log.error("Exception occurred while obtaining access token: " + e.getMessage());
        }
        return null;
    }

    @Override
    public TokenResponse getAccessTokenByRefreshToken(String refreshToken) {
        String tokenUrl = keycloakBaseUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        MultiValueMap<String, String> body = createBody("refresh_token");
        body.add("refresh_token", refreshToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, requestEntity, Map.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, String> responseBody = response.getBody();
                if (responseBody != null) {
                    return new TokenResponse(responseBody.get("access_token"), responseBody.get("refresh_token"));
                }
            }
            log.error("Failed to obtain access token using refresh token. Status code: " + response.getStatusCode());
        } catch (Exception e) {
            log.error("Exception occurred while obtaining access token using refresh token: " + e.getMessage());
        }
        return null;
    }
    public Map<String, Object> createUserPayload(UserRegisterRequest request){
        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("username", request.getUsername());
        userPayload.put("enabled", true);
        userPayload.put("email", request.getEmail());
        userPayload.put("credentials", List.of(Map.of("type", "password", "value", request.getPassword(), "temporary", false)));
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("phone", List.of(request.getPhone()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dobFormatted = request.getDob().format(formatter);
        attributes.put("dob", List.of(dobFormatted));
        userPayload.put("attributes", attributes);

        return userPayload;
    }

    public MultiValueMap<String, String> createBody(String grantType){
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", grantType);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        return body;
    }

    public String extractUserIdFromLocation(String locationHeader) {
        if (locationHeader == null) {
            throw new RuntimeException("Location header is missing in the response");
        }
        return locationHeader.substring(locationHeader.lastIndexOf('/') + 1);
    }
}
