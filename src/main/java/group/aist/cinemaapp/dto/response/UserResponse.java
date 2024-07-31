package group.aist.cinemaapp.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class UserResponse {

    String id;
    String username;
    String fullName;
    String phone;
    String mail;
    @JsonFormat(pattern = "dd.MM.yyyy")
    LocalDate dob;
}
