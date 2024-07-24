package group.aist.cinemaapp.service;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.UserRegisterRequest;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.CurrencyType;
import group.aist.cinemaapp.model.User;
import group.aist.cinemaapp.model.UserBalance;

public interface UserService {

    User getUserById(String id);

    UserBalance getUserBalanceByUserIdAndCurrency(String userId, CurrencyType currency);
    PageableResponse<User> getUsers(PageCriteria pageCriteria);

    User AddKeycloakUserToDB(String userId,UserRegisterRequest request);

}
