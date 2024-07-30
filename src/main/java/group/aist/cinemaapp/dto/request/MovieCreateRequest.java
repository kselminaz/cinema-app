package group.aist.cinemaapp.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class MovieCreateRequest {

    String name;
    String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime releaseTime;
    Integer duration;
    Integer ageLimit;
    @NotEmpty(message = "At least one movie language is required")
    List<MovieLanguageRequest> languages;
    List<Long> subtitleLanguages;
}
