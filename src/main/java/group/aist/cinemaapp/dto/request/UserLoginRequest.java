package group.aist.cinemaapp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UserLoginRequest {

    @NotBlank(message = "Username should not be null")
    String username;

    @NotBlank(message = "Password should not be null")
    @Size(min = 8,message = "Password should be contains at least 8 characters")
    String password;
}
