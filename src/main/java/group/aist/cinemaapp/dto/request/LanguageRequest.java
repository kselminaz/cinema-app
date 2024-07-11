package group.aist.cinemaapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class LanguageRequest {

    @NotBlank(message = "Iso Code is required")
    String isoCode;

    @NotBlank(message = "Title is required")
    String title;

}
