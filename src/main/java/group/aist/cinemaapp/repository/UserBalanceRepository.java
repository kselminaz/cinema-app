package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.enums.CurrencyType;
import group.aist.cinemaapp.model.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Long> {

   UserBalance findByUserIdAndCurrency(String userId, CurrencyType currency);

   UserBalance findByUserId(String id);

}
