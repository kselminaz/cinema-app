package group.aist.cinemaapp.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class MovieSessionCreateRequest {

    @NotNull(message = "Movie is required")
    @Positive(message = "Movie id must be greater than 0")
    Long movieId;

    @NotNull(message = "Hall is required")
    @Positive(message = "Hall id must be greater than 0")
    Long hallId;

    @NotNull(message = "Movie Language is required")
    @Positive(message = "Movie Language Id must be greater than 0")
    Long movieLangId;

    @Positive(message = "Movie Subtitle Language Id must be greater than 0")
    Long movieSubtitleLangId;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm a")
    LocalDateTime dateTime;



}
