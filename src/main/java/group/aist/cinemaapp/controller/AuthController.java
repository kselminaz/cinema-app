package group.aist.cinemaapp.controller;

import group.aist.cinemaapp.dto.request.UserLoginRequest;
import group.aist.cinemaapp.dto.request.UserRegisterRequest;
import group.aist.cinemaapp.dto.response.TokenResponse;
import group.aist.cinemaapp.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> createUser(@RequestBody UserRegisterRequest userDto) {
        log.info("Creating user: {}", userDto);
        try {
            String accessToken = authService.createUser(userDto);
            return ResponseEntity.ok("Access token: " + accessToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody UserLoginRequest request) {
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
}
