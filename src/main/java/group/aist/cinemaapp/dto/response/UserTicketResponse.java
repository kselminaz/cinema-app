package group.aist.cinemaapp.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import group.aist.cinemaapp.enums.UserTicketStatus;
import group.aist.cinemaapp.model.User;
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
public class UserTicketResponse {

    Long id;

    UserResponse user;

    TicketResponse ticket;

    UserTicketStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm")
    LocalDateTime createdAt;


}

