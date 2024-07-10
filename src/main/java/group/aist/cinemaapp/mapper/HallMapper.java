package group.aist.cinemaapp.mapper;

import group.aist.cinemaapp.dto.request.HallRequest;
import group.aist.cinemaapp.dto.response.HallResponse;
import group.aist.cinemaapp.model.Hall;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface HallMapper {
    HallMapper INSTANCE = Mappers.getMapper(HallMapper.class);

    HallResponse toResponseDto(Hall hall);
    Hall toEntity(HallRequest hallRequestDto);
}

