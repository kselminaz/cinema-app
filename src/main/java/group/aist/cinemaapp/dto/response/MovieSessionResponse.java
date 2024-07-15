package group.aist.cinemaapp.dto.response;

import group.aist.cinemaapp.enums.MovieSessionStatus;
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
public class MovieSessionResponse {

    Long id;

    MovieResponse movie;

    String language;

    String subtitleLanguage;

    LocalDateTime datetime;

    MovieSessionStatus status;

}

