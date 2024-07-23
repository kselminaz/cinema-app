package group.aist.cinemaapp.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HallUpdateRequest {
    @NotBlank(message = "Name is required")
    String name;
    @NotNull(message = "Total seats count is required")
    Integer totalSeatsCount;
    String status;
}

