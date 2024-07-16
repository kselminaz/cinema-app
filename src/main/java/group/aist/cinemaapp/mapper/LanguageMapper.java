package group.aist.cinemaapp.mapper;

import group.aist.cinemaapp.dto.request.LanguageCreateRequest;
import group.aist.cinemaapp.dto.response.LanguageResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.LanguageStatus;
import group.aist.cinemaapp.model.Language;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring",uses={LanguageStatus.class})
public interface LanguageMapper {

    Language toEntity(LanguageCreateRequest request);

    @Mapping(target = "status", source = "status", qualifiedByName = "getById")
    LanguageResponse toResponse(Language entity);

    @Mapping(target = "data", source = "content", qualifiedByName = "getLanguageList")
    @Mapping(target = "hasNextPage", expression = "java(page.hasNext())")
    @Mapping(target = "lastPageNumber", source = "totalPages")
    @Mapping(target = "totalElements", source = "totalElements")
    PageableResponse<LanguageResponse> toPageableResponse(Page<Language> page);

    @Named("getById")
    default LanguageStatus getById(Integer id) {
        return LanguageStatus.getStatusById(id);
    }

    @Named("getLanguageList")
    default List<LanguageResponse> getLanguageList(List<Language> languages) {
        return languages.stream().map(this::toResponse).toList();
    }

}
