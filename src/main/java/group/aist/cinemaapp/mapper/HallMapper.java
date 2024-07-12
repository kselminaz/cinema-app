package group.aist.cinemaapp.mapper;
import group.aist.cinemaapp.dto.request.HallRequest;
import group.aist.cinemaapp.dto.response.HallResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.HallStatus;
import group.aist.cinemaapp.model.Hall;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel="spring")
public interface HallMapper {
    Hall toEntity(HallRequest request );

    Hall toUpdatedEntity(HallRequest request );

    @Mapping(target = "status", source = "status", qualifiedByName = "getById")
    HallResponse toResponse(Hall entity);


    /* @Mapping(target = "data", source = "content", qualifiedByName = "getLanguageList")*/
    @Mapping(target = "hasNextPage", expression = "java(page.hasNext())")
    @Mapping(target = "lastPageNumber", source = "totalPages")
    @Mapping(target = "totalElements", source = "totalElements")
    PageableResponse<Hall> toPageableResponse(Page<Hall> page);

//    @Named("getById")
//    default HallStatus getById(Integer id) {
//        return HallStatus.getStatusById(id);
//    }

    @Named("getLanguageList")
    default List<HallResponse> getLanguageList(List<Hall> languages) {
        return languages.stream().map(this::toResponse).toList();
    }

}