
package group.aist.cinemaapp.dto.request;

import group.aist.cinemaapp.enums.CurrencyType;
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
public class TicketUpdateRequest {

    @Positive(message = "Seat id must be greater than 0")
    Long seatId;
    @Positive(message = "Session id must be greater than 0")
    Long sessionId;
    @Positive(message = "Price must be positive")
    BigDecimal price;

    CurrencyType currency;

    String status;
}
