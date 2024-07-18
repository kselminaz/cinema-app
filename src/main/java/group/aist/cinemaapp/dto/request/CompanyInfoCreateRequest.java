package group.aist.cinemaapp.dto.request;

import jakarta.validation.constraints.NotBlank;
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
public class CompanyInfoCreateRequest {
    @NotBlank(message = "Name is required")
    String name;
    @NotBlank(message = "Text is required")
    String aboutText;
    @NotBlank(message = "Logo is required")
    String logo;
    @NotBlank(message = "Costumers information is required")
    String costumersInformationText;
}
