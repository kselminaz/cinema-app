package group.aist.cinemaapp.dto.request;

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
public class TicketCreateRequest {

    @NotNull(message = "Seat is required")
    @Positive(message = "Seat id must be greater than 0")
    Long seatId;
    @NotNull(message = "MovieSession is required")
    @Positive(message = "MovieSession id must be greater than 0")
    Long sessionId;

    @NotNull(message = "Price is required")
    BigDecimal price;
}
