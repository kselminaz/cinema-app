package group.aist.cinemaapp.service;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.MovieRequest;
import group.aist.cinemaapp.dto.response.MovieResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.model.Movie;

public interface MovieService {

    MovieResponse getMovieById(Long id);

    PageableResponse<MovieResponse> getMovies(PageCriteria pageCriteria);

    void saveMovie(MovieRequest movieRequest);

    void updateMovie(Long id, MovieRequest movieRequest);

    void updateMovieStatus(Long id, String status);

    void deleteMovieById(Long id);

    Movie getMovieIfExist(Long id);
}
