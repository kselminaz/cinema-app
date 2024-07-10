package group.aist.cinemaapp.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HallRequest {
     String name;
     Integer totalSeatsCount;
     Integer status;

}
