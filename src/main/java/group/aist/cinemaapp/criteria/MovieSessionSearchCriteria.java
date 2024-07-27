package group.aist.cinemaapp.criteria;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieSessionSearchCriteria {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime searchDateTime;

    private String movieName;

    private  String movieLanguage;

    private String status;

}
