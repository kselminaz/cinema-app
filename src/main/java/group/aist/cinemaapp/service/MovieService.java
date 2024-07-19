package group.aist.cinemaapp.service;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.MovieCreateRequest;
import group.aist.cinemaapp.dto.request.MovieLanguageRequest;
import group.aist.cinemaapp.dto.request.MovieUpdateRequest;
import group.aist.cinemaapp.dto.response.MovieResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.model.Movie;
import group.aist.cinemaapp.model.MovieLanguage;
import org.springframework.web.multipart.MultipartFile;

public interface MovieService {

    MovieResponse getMovieById(Long id);

    PageableResponse<MovieResponse> getMovies(PageCriteria pageCriteria);

    void saveMovie(MovieCreateRequest movieCreateRequest, MultipartFile file);

    void updateMovie(Long id, MovieUpdateRequest movieUpdateRequest);

    void updateMovieStatus(Long id, String status);

    void deleteMovieById(Long id);

    MovieLanguage addMovieLanguage(Movie movie, MovieLanguageRequest request);

    Movie fetchMovieIfExist(Long id);
}
