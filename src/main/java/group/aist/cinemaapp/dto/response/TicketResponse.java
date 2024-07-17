package group.aist.cinemaapp.dto.response;

import group.aist.cinemaapp.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class TicketResponse {

    Long id;
    String seat;
    BigDecimal price;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    TicketStatus status;
    MovieSessionResponse session;
}
