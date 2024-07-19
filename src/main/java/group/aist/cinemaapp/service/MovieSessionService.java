package group.aist.cinemaapp.service;

import group.aist.cinemaapp.criteria.MovieSessionSortingCriteria;
import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.MovieSessionCreateRequest;
import group.aist.cinemaapp.dto.request.MovieSessionUpdateRequest;
import group.aist.cinemaapp.dto.response.MovieSessionResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.model.MovieSession;

public interface MovieSessionService {

    MovieSessionResponse getMovieSessionById(Long id);

    PageableResponse<MovieSessionResponse> getMovieSessions(PageCriteria pageCriteria, MovieSessionSortingCriteria criteria);

    void saveMovieSession(MovieSessionCreateRequest movieRequest);

    void updateMovieSession(Long id, MovieSessionUpdateRequest movieRequest);

    void updateMovieSessionWithStatus(Long id, String status);

    void deleteMovieSession(Long id);

    MovieSession fetchMovieSessionIfExist(Long id);
}
