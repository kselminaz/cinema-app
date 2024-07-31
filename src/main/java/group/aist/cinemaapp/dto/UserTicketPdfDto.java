package group.aist.cinemaapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    String companyAboutText;
    String companyImage;
    String movieName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDateTime movieDateTime;
    BigDecimal price;
    String currency;
    String qrCode;
    String ticketNumber;
    String hall;
    String sectorName;
    String row;
    Long seatNumber;
    String customersInfo;


}
