package group.aist.cinemaapp.service.impl;

import group.aist.cinemaapp.annotation.Log;
import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.UserRegisterRequest;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.CurrencyType;
import group.aist.cinemaapp.mapper.UserMapper;
import group.aist.cinemaapp.model.User;
import group.aist.cinemaapp.model.UserBalance;
import group.aist.cinemaapp.repository.UserBalanceRepository;
import group.aist.cinemaapp.repository.UserRepository;
import group.aist.cinemaapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Set;

import static group.aist.cinemaapp.enums.CurrencyType.AZN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Log
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserBalanceRepository userBalanceRepository;

    private final UserMapper userMapper;

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, String.format(
                "User with id [%s] was not found!", id
        )));
    }

    @Override
    public UserBalance getUserBalanceByUserIdAndCurrency(String userId, CurrencyType currency) {
        return userBalanceRepository.findByUserIdAndCurrency(userId, currency);
    }


    @Override
    public PageableResponse<User> getUsers(PageCriteria pageCriteria) {
        return null;
    }

    @Override
    public User AddKeycloakUserToDB(String userId, UserRegisterRequest request) {
        var user = userMapper.toUserEntity(request);
        user.setId(userId);
        userRepository.save(user);

       // var userBalance = userMapper.toUserBalanceEntity(user);
        var userBalance= UserBalance.builder().user(user).amount(BigDecimal.valueOf(100)).currency(AZN).build();

        user.setBalances(Set.of(userBalance));

        return user;
    }
}
