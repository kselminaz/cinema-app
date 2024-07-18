package group.aist.cinemaapp.mapper;

import group.aist.cinemaapp.dto.request.TicketCreateRequest;
import group.aist.cinemaapp.dto.response.TicketResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.TicketStatus;
import group.aist.cinemaapp.model.Seat;
import group.aist.cinemaapp.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TicketStatus.class, Seat.class, MovieSessionMapper.class})
public interface TicketMapper {

    @Mapping(target = "seat", ignore = true)
    @Mapping(target = "status", ignore = true)
    Ticket toEntity(TicketCreateRequest request);

    @Mapping(target = "status", source = "status", qualifiedByName = "getById")
    @Mapping(target = "session", source = "session")
    @Mapping(target = "seat", expression = "java(entity.getSeat().getSeat_number().toString())")
    TicketResponse toResponse(Ticket entity);

    @Mapping(target = "data", source = "content", qualifiedByName = "getDataList")
    @Mapping(target = "hasNextPage", expression = "java(page.hasNext())")
    @Mapping(target = "lastPageNumber", source = "totalPages")
    @Mapping(target = "totalElements", source = "totalElements")
    PageableResponse<TicketResponse> toPageableResponse(Page<Ticket> page);

    @Named("getById")
    default TicketStatus getById(Integer id) {
        return TicketStatus.getStatusById(id);
    }

    @Named("getDataList")
    default List<TicketResponse> getDataList(List<Ticket> data) {
        return data.stream().map(this::toResponse).toList();
    }
}