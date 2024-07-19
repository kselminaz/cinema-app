package group.aist.cinemaapp.mapper;

import group.aist.cinemaapp.dto.request.MovieSessionCreateRequest;
import group.aist.cinemaapp.dto.response.MovieSessionResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.MovieSessionStatus;
import group.aist.cinemaapp.model.Hall;
import group.aist.cinemaapp.model.Language;
import group.aist.cinemaapp.model.MovieLanguage;
import group.aist.cinemaapp.model.MovieSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MovieSessionStatus.class, MovieMapper.class, MovieLanguage.class, Language.class, Hall.class})

public interface MovieSessionMapper {

    @Mapping(target = "movie", ignore = true)
    @Mapping(target = "hall", ignore = true)
    @Mapping(target = "language", ignore = true)
    @Mapping(target = "subtitleLanguage", ignore = true)
    @Mapping(target = "status", ignore = true)
    MovieSession toEntity(MovieSessionCreateRequest request);

    @Mapping(target = "status", source = "status", qualifiedByName = "getById")
    @Mapping(target = "movie", source = "movie")
    @Mapping(target = "language", expression = "java(entity.getLanguage().getLanguage().getTitle())")
    @Mapping(target = "subtitleLanguage", expression = "java(entity.getSubtitleLanguage()!=null ? entity.getSubtitleLanguage().getTitle() : null)")
    @Mapping(target = "hall", expression = "java(entity.getHall().getName())")
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
    default List<MovieSessionResponse> getMovieSessionList(List<MovieSession> data) {
        return data.stream().map(this::toResponse).toList();
    }

    @Named("getSessionHall")
    default String getSessionHall(Hall hall) {
        return hall.getName();
    }

    @Named("getSessionSubtitleLanguage")
    default String getSessionSubtitleLanguage(Language subtitleLanguage) {
        return subtitleLanguage.getTitle();
    }

}
