package group.aist.cinemaapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class MovieResponse {

    Long id;
    String name;
    String image;
    String description;
    LocalDateTime releaseTime;
    Integer duration;
    Integer ageLimit;
    String status;
}
