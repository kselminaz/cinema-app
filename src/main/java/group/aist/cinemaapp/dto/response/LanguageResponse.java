package group.aist.cinemaapp.dto.response;

import group.aist.cinemaapp.enums.LanguageStatus;
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
public class LanguageResponse{

    Long id;
    String iso_code;
    String title;
    LanguageStatus status;
}

