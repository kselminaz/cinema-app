package group.aist.cinemaapp.service.impl;

import group.aist.cinemaapp.enums.CurrencyType;
import group.aist.cinemaapp.model.UserBalance;
import group.aist.cinemaapp.repository.UserBalanceRepository;
import group.aist.cinemaapp.service.UserBalanceService;
import group.aist.cinemaapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserBalanceServiceImpl implements UserBalanceService {

    private final UserBalanceRepository userBalanceRepository;
    private final UserService userService;

    @Override
    public UserBalance getUserBalanceById(Long id) {
        return userBalanceRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, String.format(
                "User Balance with id [%d] was not found!", id
        )));
    }

    @Override
    public UserBalance getUserBalanceByUserAndCurrency(String userId, CurrencyType currency) {
        return userBalanceRepository.findByUserIdAndCurrency(userId, currency);
    }

    @Override
    public UserBalance saveUserBalance(String userId, CurrencyType currencyType, BigDecimal amount) {
        var user = userService.getUserById(userId);
        var userBalance = UserBalance.builder().user(user).currency(currencyType).amount(amount).build();
        return userBalance;
    }

    @Override
    public void updateUserBalance(Long id,BigDecimal amount) {

        var userBalance=getUserBalanceById(id);
        userBalance.setAmount(amount);


    }

}
