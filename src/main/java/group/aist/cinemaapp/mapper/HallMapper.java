package group.aist.cinemaapp.mapper;
import group.aist.cinemaapp.dto.request.HallRequest;
import group.aist.cinemaapp.dto.response.HallResponse;
import group.aist.cinemaapp.model.Hall;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel="spring")
public interface HallMapper {
    Hall toEntity(HallRequest request);
    @Mapping(target = "status", ignore = true)
    HallResponse toResponse(Hall entity);


}