package group.aist.cinemaapp.dto.request;

import group.aist.cinemaapp.enums.CurrencyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class UserBalanceUpdateRequest {

    @NotNull(message = "Amount is required")
    BigDecimal amount;

    @NotBlank
    CurrencyType currency;
}
