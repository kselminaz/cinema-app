package group.aist.cinemaapp.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HallResponse {
     Long id;
     String name;
     Integer totalSeatsCount;
     Integer status;
     LocalDateTime createdAt;
     LocalDateTime updatedAt;
}
