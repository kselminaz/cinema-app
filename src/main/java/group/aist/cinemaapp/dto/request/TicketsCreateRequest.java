package group.aist.cinemaapp.dto.request;

import group.aist.cinemaapp.enums.CurrencyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class TicketsCreateRequest {

    @NotEmpty(message = "At least one seat id is required")
    List<Long> seatIds;

    @NotNull(message = "MovieSession is required")
    @Positive(message = "MovieSession id must be greater than 0")
    Long sessionId;

    @NotNull(message = "Price is required")
    BigDecimal price;

    @NotBlank
    CurrencyType currency;
}
