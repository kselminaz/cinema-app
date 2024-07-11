package group.aist.cinemaapp.mapper;

import group.aist.cinemaapp.dto.request.LanguageRequest;
import group.aist.cinemaapp.dto.response.LanguageResponse;
import group.aist.cinemaapp.model.Language;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LanguageMapper {

    Language toEntity(LanguageRequest request );

    @Mapping(target = "status",ignore = true)
    LanguageResponse toResponse(Language entity);


}
