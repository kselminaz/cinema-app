package group.aist.cinemaapp.service;

import group.aist.cinemaapp.enums.CurrencyType;
import group.aist.cinemaapp.model.UserBalance;

import java.math.BigDecimal;

public interface UserBalanceService {

    UserBalance getUserBalanceById(Long id);

    UserBalance getUserBalanceByUserAndCurrency(String userId, CurrencyType currency);

    UserBalance saveUserBalance(String userId, CurrencyType currencyType, BigDecimal amount);

    void updateUserBalance(Long id, BigDecimal amount);
}
