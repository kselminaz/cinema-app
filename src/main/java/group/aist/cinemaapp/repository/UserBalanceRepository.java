package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.enums.CurrencyType;
import group.aist.cinemaapp.model.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Long> {

   UserBalance findByUserIdAndCurrency(Long userId, CurrencyType currency);

}
