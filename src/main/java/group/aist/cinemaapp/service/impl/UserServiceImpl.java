package group.aist.cinemaapp.service.impl;

import group.aist.cinemaapp.annotation.Log;
import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.CurrencyType;
import group.aist.cinemaapp.model.User;
import group.aist.cinemaapp.model.UserBalance;
import group.aist.cinemaapp.repository.UserBalanceRepository;
import group.aist.cinemaapp.repository.UserRepository;
import group.aist.cinemaapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Log
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserBalanceRepository userBalanceRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, String.format(
                "User with id [%d] was not found!", id
        )));
    }

    @Override
    public UserBalance getUserBalanceByUserIdAndCurrency(Long userId, CurrencyType currency) {
        return userBalanceRepository.findByUserIdAndCurrency(userId,currency);
    }

    @Override
    public PageableResponse<User> getUsers(Long userId, PageCriteria pageCriteria) {
        return null;
    }
}
