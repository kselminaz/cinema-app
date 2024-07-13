package group.aist.cinemaapp.mapper;

import group.aist.cinemaapp.dto.request.MovieRequest;
import group.aist.cinemaapp.dto.response.MovieResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.MovieStatus;
import group.aist.cinemaapp.model.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "getById")
    MovieResponse toMovieResponse(Movie movie);

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
}
