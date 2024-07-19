package group.aist.cinemaapp.mapper;

import group.aist.cinemaapp.dto.request.HallCreateRequest;
import group.aist.cinemaapp.dto.response.HallResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.HallStatus;
import group.aist.cinemaapp.model.Hall;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = {HallStatus.class})
public interface HallMapper {


    @Mapping(target = "status", ignore = true)
    Hall toEntity(HallCreateRequest request);

    @Mapping(target = "status", source = "status", qualifiedByName = "hallById")
    HallResponse toResponse(Hall entity);

    @Mapping(target = "data", source = "content", qualifiedByName = "getHallList")
    @Mapping(target = "hasNextPage", expression = "java(page.hasNext())")
    @Mapping(target = "lastPageNumber", source = "totalPages")
    @Mapping(target = "totalElements", source = "totalElements")
    PageableResponse<HallResponse> toPageableResponse(Page<Hall> page);

    @Named("hallById")
    default HallStatus hallById(Integer id) {
        return HallStatus.getHallStatusById(id);
    }

    @Named("getHallList")
    default List<HallResponse> getHallList(List<Hall> halls) {
        return halls.stream().map(this::toResponse).toList();
    }
}