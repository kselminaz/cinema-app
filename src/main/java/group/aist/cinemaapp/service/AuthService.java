package group.aist.cinemaapp.service;

import group.aist.cinemaapp.dto.request.UserLoginRequest;
import group.aist.cinemaapp.dto.request.UserRegisterRequest;
import group.aist.cinemaapp.dto.response.TokenResponse;


public interface AuthService {

    String getAccessToken();
    String createUser(UserRegisterRequest request);
    TokenResponse getUserAccessToken(UserLoginRequest request);
}
