package group.aist.cinemaapp.controller;

import group.aist.cinemaapp.dto.request.UserLoginRequest;
import group.aist.cinemaapp.dto.request.UserRegisterRequest;
import group.aist.cinemaapp.dto.response.TokenResponse;
import group.aist.cinemaapp.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/create-user")
    public void createUser(@Valid @RequestBody UserRegisterRequest userDto) {
        log.info("Creating user: {}", userDto);
        authService.createUser(userDto);
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody UserLoginRequest request) {
        try {
            return authService.getUserAccessToken(request);
        } catch (Exception e) {
            log.info("return null");
            throw e;
        }
    }

    @PostMapping("/token")
    public ResponseEntity<String> getAccessToken() {
        try {
            String accessToken = authService.getAccessToken();
            return ResponseEntity.ok("Access token: " + accessToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user: " + e.getMessage());
        }
    }
    @PostMapping("/refresh-token")
    public TokenResponse getAccessTokenByRefreshToken(@RequestBody String refreshToken) {
        try {
            return authService.getAccessTokenByRefreshToken(refreshToken);
        } catch (Exception e) {
            throw e;
        }
    }

}
