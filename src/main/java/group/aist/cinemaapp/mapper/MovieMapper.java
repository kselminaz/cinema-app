package group.aist.cinemaapp.mapper;

import group.aist.cinemaapp.dto.request.MovieRequest;
import group.aist.cinemaapp.dto.response.MovieLanguageResponse;
import group.aist.cinemaapp.dto.response.MovieResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.MovieStatus;
import group.aist.cinemaapp.model.Language;
import group.aist.cinemaapp.model.Movie;
import group.aist.cinemaapp.model.MovieLanguage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "getById")
    @Mapping(target = "languages", source = "languages", qualifiedByName = "getLanguages")
    @Mapping(target = "subtitleLanguages", source = "subtitleLanguages", qualifiedByName = "getSubtitleLanguages")
    MovieResponse toMovieResponse(Movie movie);

    @Mapping(target = "subtitleLanguages", ignore = true)
    @Mapping(target = "languages", ignore = true)
    Movie toMovie(MovieRequest movieRequest);

    @Mapping(target = "movie", ignore = true)
    MovieLanguage toMovieLanguage(MovieRequest movieRequest);

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
    default List<String> getSubtitleLanguages(Set<Language> languages) {
        return languages.stream()
                .map(Language::getIsoCode)
                .collect(Collectors.toList());
    }

    @Named("getLanguages")
    default List<MovieLanguageResponse> getLanguages(Set<MovieLanguage> languages) {
        return languages.stream()
                .map(e -> new MovieLanguageResponse(e.getId(), e.getLanguage().getTitle(), e.getIsMain()))
                .collect(Collectors.toList());
    }

}
