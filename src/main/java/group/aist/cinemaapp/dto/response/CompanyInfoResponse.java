package group.aist.cinemaapp.dto.response;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyInfoResponse {
    String name;
    String aboutText;
    String logo;
    String costumersInformationText;
}
