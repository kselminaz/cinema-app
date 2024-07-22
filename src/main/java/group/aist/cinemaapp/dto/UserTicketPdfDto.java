package group.aist.cinemaapp.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserTicketPdfDto {

    String companyName;
    String companyPhone;
    String companyImage;
    String movieName;
    LocalDateTime movieDateTime;
    BigDecimal price;
    String currency;
    String qrCode;
    String ticketNumber;
    String hall;
    String sectorName;
    String row;
    Long seatNumber;


}
