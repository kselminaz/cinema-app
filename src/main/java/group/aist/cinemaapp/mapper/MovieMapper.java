package group.aist.cinemaapp.mapper;

import group.aist.cinemaapp.dto.request.MovieRequest;
import group.aist.cinemaapp.dto.response.MovieResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.MovieStatus;
import group.aist.cinemaapp.model.Language;
import group.aist.cinemaapp.model.Movie;
import group.aist.cinemaapp.service.LanguageService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "getById")
    @Mapping(target = "subtitleLanguages", source = "subtitleLanguages", qualifiedByName = "getSubtitleLanguages")
    MovieResponse toMovieResponse(Movie movie);

    @Mapping(target = "subtitleLanguages", ignore = true)
    Movie toMovie(MovieRequest movieRequest);

    @Mapping(target = "data", source = "content", qualifiedByName = "getMovieList")
    @Mapping(target = "hasNextPage", expression = "java(page.hasNext())")
    @Mapping(target = "lastPageNumber", source = "totalPages")
    @Mapping(target = "totalElements", source = "totalElements")
    PageableResponse<MovieResponse> toPageableResponse(Page<Movie> page);

    @Named("getById")
    default MovieStatus getById(Integer id) {
        return MovieStatus.findById(id);
    }

    @Named("getMovieList")
    default List<MovieResponse> getMovieList(List<Movie> movies) {
        return movies.stream().map(this::toMovieResponse).toList();
    }
    @Named("getSubtitleLanguages")
    default List<String> getSubtitleLanguages(List<Language> languages) {
        return languages.stream()
                .map(Language::getIsoCode)
                .collect(Collectors.toList());
    }

//    @Named("getSubtitleLanguagesByIds")
//    default List<Language> getLanguagesByIds(List<Long> languageIds) {
//        return languageIds.stream()
//                .map(languageService::getLanguageById)
//                .map(languageMapper::toLanguage)
//                .collect(Collectors.toList());}
}
