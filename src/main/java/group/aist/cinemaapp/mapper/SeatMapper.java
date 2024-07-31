package group.aist.cinemaapp.mapper;

import group.aist.cinemaapp.dto.request.ManySeatCreateRequest;
import group.aist.cinemaapp.dto.request.SeatCreateRequest;
import group.aist.cinemaapp.dto.response.MovieResponse;
import group.aist.cinemaapp.dto.response.SeatResponse;
import group.aist.cinemaapp.enums.SeatStatus;
import group.aist.cinemaapp.model.Movie;
import group.aist.cinemaapp.model.Seat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    @Mapping(target = "status", source = "status", qualifiedByName = "getById")
    @Mapping(target = "sectorName", source = "sector.name")
    SeatResponse toSeatResponse(Seat seat);

    @Mapping(target = "sector", ignore = true)
    Seat toSeat(SeatCreateRequest seatCreateRequest);

    @Mapping(target = "sector", ignore = true)
    @Mapping(target = "seatNumber", ignore = true)
    Seat toSeat(ManySeatCreateRequest manySeatCreateRequest);

    @Named("getById")
    default SeatStatus getById(Integer id) {
        return SeatStatus.findById(id);
    }

    @Named("getSeatList")
    default List<SeatResponse> getSeatList(List<Seat> seats) {
        return seats.stream().map(this::toSeatResponse).toList();
    }

}
