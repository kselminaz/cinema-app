package group.aist.cinemaapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HallRequest {
     @NotBlank(message = "Name cannot be blank")
     String name;
     @NotNull(message = "Total seats count cannot be null")
     Integer totalSeatsCount;
     @NotNull(message = "Status cannot be null")
     Integer status;

}
