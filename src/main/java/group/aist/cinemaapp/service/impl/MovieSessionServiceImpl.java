package group.aist.cinemaapp.service.impl;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.MovieSessionCreateRequest;
import group.aist.cinemaapp.dto.request.MovieSessionUpdateRequest;
import group.aist.cinemaapp.dto.response.MovieSessionResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.MovieSessionStatus;
import group.aist.cinemaapp.mapper.MovieSessionMapper;
import group.aist.cinemaapp.model.MovieSession;
import group.aist.cinemaapp.repository.MovieSessionRepository;
import group.aist.cinemaapp.service.LanguageService;
import group.aist.cinemaapp.service.MovieService;
import group.aist.cinemaapp.service.MovieSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MovieSessionServiceImpl implements MovieSessionService {

    private final MovieSessionRepository movieSessionRepository;
    private final MovieSessionMapper movieSessionMapper;
    private final LanguageService languageService;
    private final MovieService movieService;


    @Override
    public MovieSessionResponse getMovieSessionById(Long id) {
        return null;
    }

    @Override
    public PageableResponse<MovieSessionResponse> getMovieSessions(PageCriteria pageCriteria) {
        return null;
    }

    @Override
    public void saveMovieSession(MovieSessionCreateRequest movieRequest) {

    }

    @Override
    public void updateMovieSession(Long id, MovieSessionUpdateRequest movieRequest) {

    }

    @Override
    public void updateMovieSessionWithStatus(Long id, String status) {

    }

    @Override
    public void deleteMovieSession(Long id) {

    }

    @Override
    public MovieSession fetchMovieSessionIfExist(Long id) {
        return null;
    }
}
