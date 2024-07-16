package group.aist.cinemaapp.mapper;

import group.aist.cinemaapp.dto.request.SeatRequest;
import group.aist.cinemaapp.dto.response.SeatResponse;
import group.aist.cinemaapp.enums.SeatStatus;
import group.aist.cinemaapp.model.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface SeatMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "getById")
    SeatResponse toSeatResponse(Seat seat);

    Seat toSeat(SeatRequest seatRequest);

    @Named("getById")
    default SeatStatus getById(Integer id) {
        return SeatStatus.findById(id);
    }
}
