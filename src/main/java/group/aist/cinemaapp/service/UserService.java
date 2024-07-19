package group.aist.cinemaapp.service;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.CurrencyType;
import group.aist.cinemaapp.model.User;
import group.aist.cinemaapp.model.UserBalance;

public interface UserService {

    User getUserById(Long id);

    UserBalance getUserBalanceByUserIdAndCurrency(Long userId, CurrencyType currency);
    PageableResponse<User> getUsers(Long userId, PageCriteria pageCriteria);

}
