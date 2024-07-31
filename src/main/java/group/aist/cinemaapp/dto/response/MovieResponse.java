package group.aist.cinemaapp.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime releaseTime;
    Integer duration;
    Integer ageLimit;
    List<MovieLanguageResponse> languages;
    List<String> subtitleLanguages;
    String status;
}
