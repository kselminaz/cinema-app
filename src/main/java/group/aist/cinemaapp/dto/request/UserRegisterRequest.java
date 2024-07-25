package group.aist.cinemaapp.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class UserRegisterRequest {

    @NotBlank(message = "Username should not be null")
    String username;

    String lastName;

    String firstName;

    @NotBlank(message = "Password should not be null")
    @Size(min = 8,message = "Password should be contains at least 8 characters")
    String password;

    @NotBlank(message = "Mail should not be null")
    @Email(message = "This field should be mail")
    String email;


    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dob;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\+\\d{12}$", message = "Phone number should start with '+' and have exactly 12 digits")
    String phone;

}
