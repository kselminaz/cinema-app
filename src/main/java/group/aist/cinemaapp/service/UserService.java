package group.aist.cinemaapp.service;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.UserRegisterRequest;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.model.User;

public interface UserService {

    User getUserById(String id);

    PageableResponse<User> getUsers(PageCriteria pageCriteria);

    User AddKeycloakUserToDB(String userId, UserRegisterRequest request);

}
