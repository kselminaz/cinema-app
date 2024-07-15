package group.aist.cinemaapp.dto.response;

import group.aist.cinemaapp.enums.HallStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HallResponse {
     Long id;
     String name;
     Integer totalSeatsCount;
     HallStatus status;
     LocalDateTime createdAt;
     LocalDateTime updatedAt;
}
