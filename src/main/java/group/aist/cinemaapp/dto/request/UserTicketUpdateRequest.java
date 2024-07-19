package group.aist.cinemaapp.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class UserTicketUpdateRequest {

    @NotNull(message = "User id is required")
    @Positive(message = "User id must be greater than 0")
    Long userId;

    @NotEmpty(message = "At least one ticket id  is required")
    Set<Long> ticketId;

    String status;


}
