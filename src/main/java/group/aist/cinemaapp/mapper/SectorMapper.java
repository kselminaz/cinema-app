package group.aist.cinemaapp.mapper;

import group.aist.cinemaapp.dto.request.SectorCreateRequest;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.dto.response.SectorResponse;
import group.aist.cinemaapp.enums.SectorStatus;
import group.aist.cinemaapp.model.Sector;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SectorStatus.class})
public interface SectorMapper {
    Sector toEntity(SectorCreateRequest request);

    @Mapping(target = "status", source = "status", qualifiedByName = "getById")
    SectorResponse toResponse(Sector entity);

    @Mapping(target = "data", source = "content", qualifiedByName = "getSectorList")
    @Mapping(target = "hasNextPage", expression = "java(page.hasNext())")
    @Mapping(target = "lastPageNumber", source = "totalPages")
    @Mapping(target = "totalElements", source = "totalElements")
    PageableResponse<SectorResponse> toPageableResponse(Page<Sector> page);

    @Named("getById")
    default SectorStatus getById(Integer id) {
        return SectorStatus.getSectorStatusById(id);
    }

    @Named("getSectorList")
    default List<SectorResponse> getSectorList(List<Sector> sectors) {
        return sectors.stream().map(this::toResponse).toList();
    }
}
