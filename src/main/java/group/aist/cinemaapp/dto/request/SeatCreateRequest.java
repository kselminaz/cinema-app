package group.aist.cinemaapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class SeatCreateRequest {

    @NotBlank(message = "Row is required")
    String row;

    @NotNull(message = "Seat number is required")
    Long seat_number;

    @NotNull(message = "Seat number is required")
    Long sector;
}
