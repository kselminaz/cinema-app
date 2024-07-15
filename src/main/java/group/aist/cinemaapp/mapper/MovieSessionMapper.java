package group.aist.cinemaapp.mapper;

import group.aist.cinemaapp.dto.request.LanguageCreateRequest;
import group.aist.cinemaapp.dto.request.MovieSessionCreateRequest;
import group.aist.cinemaapp.dto.response.LanguageResponse;
import group.aist.cinemaapp.dto.response.MovieResponse;
import group.aist.cinemaapp.dto.response.MovieSessionResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.LanguageStatus;
import group.aist.cinemaapp.enums.MovieSessionStatus;
import group.aist.cinemaapp.model.Language;
import group.aist.cinemaapp.model.Movie;
import group.aist.cinemaapp.model.MovieSession;
import group.aist.cinemaapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring",uses={MovieSessionStatus.class})
public interface MovieSessionMapper {


    /*MovieSession toEntity(MovieSessionCreateRequest request );

    @Mapping(target = "status", source = "status", qualifiedByName = "getById")
    MovieSessionResponse toResponse(MovieSession entity);

    @Mapping(target = "data", source = "content", qualifiedByName = "getDataList")
    @Mapping(target = "hasNextPage", expression = "java(page.hasNext())")
    @Mapping(target = "lastPageNumber", source = "totalPages")
    @Mapping(target = "totalElements", source = "totalElements")
    PageableResponse<MovieSessionResponse> toPageableResponse(Page<MovieSession> page);

    @Named("getById")
    default MovieSessionStatus getById(Integer id) {
        return MovieSessionStatus.getStatusById(id);
    }

    @Named("getDataList")
    default List<MovieSessionResponse> getLanguageList(List<MovieSession> data) {
        return data.stream().map(this::toResponse).toList();
    }*/

}
