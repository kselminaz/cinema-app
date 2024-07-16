package group.aist.cinemaapp.service.impl;

import group.aist.cinemaapp.criteria.MovieSessionSortingCriteria;
import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.MovieSessionCreateRequest;
import group.aist.cinemaapp.dto.request.MovieSessionUpdateRequest;
import group.aist.cinemaapp.dto.response.MovieSessionResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.MovieSessionStatus;
import group.aist.cinemaapp.mapper.MovieSessionMapper;
import group.aist.cinemaapp.model.MovieSession;
import group.aist.cinemaapp.repository.MovieSessionRepository;
import group.aist.cinemaapp.service.HallService;
import group.aist.cinemaapp.service.MovieService;
import group.aist.cinemaapp.service.MovieSessionService;
import group.aist.cinemaapp.util.SortingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

import static group.aist.cinemaapp.enums.LanguageStatus.VISIBLE;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MovieSessionServiceImpl implements MovieSessionService {

    private final MovieSessionRepository movieSessionRepository;
    private final MovieSessionMapper movieSessionMapper;
    private final HallService hallService;
    private final MovieService movieService;
    private final SortingUtil sortingUtil;


    @Override
    @Transactional
    public MovieSessionResponse getMovieSessionById(Long id) {
        var entity = fetchMovieSessionIfExist(id);
        return movieSessionMapper.toResponse(entity);

    }

    @Override
    @Transactional
    public PageableResponse<MovieSessionResponse> getMovieSessions(PageCriteria pageCriteria, MovieSessionSortingCriteria sortingCriteria) {

        var orders = sortingUtil.buildSortOrdersForMovieSessions(sortingCriteria);
        var resultsPage = movieSessionRepository.findAllByStatusIs(PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount(), Sort.by(orders)), VISIBLE.getId());
        return movieSessionMapper.toPageableResponse(resultsPage);
    }

    @Override
    @Transactional
    public void saveMovieSession(MovieSessionCreateRequest request) {
        var entity = new MovieSession();
        entity.setDatetime(request.getDatetime());
        entity.setStatus(MovieSessionStatus.VISIBLE.getId());
        addRelations(entity, request.getMovieId(), request.getHallId(), request.getMovieLangId(), request.getMovieSubtitleLangId());
        movieSessionRepository.save(entity);
    }

    @Override
    @Transactional
    public void updateMovieSession(Long id, MovieSessionUpdateRequest request) {

        var entity = fetchMovieSessionIfExist(id);
        ofNullable(request.getDateTime()).ifPresent(entity::setDatetime);
        ofNullable(request.getStatus()).ifPresent(status -> entity
                .setStatus(MovieSessionStatus.valueOf(request.getStatus()).getId()));
        addRelations(entity, request.getMovieId(), request.getHallId(), request.getMovieLangId(), request.getMovieSubtitleLangId());
        movieSessionRepository.save(entity);

    }

    @Override
    public void updateMovieSessionWithStatus(Long id, String status) {

        var entity = fetchMovieSessionIfExist(id);
        var sessionStatus = Arrays.stream(MovieSessionStatus.values()).filter(lang -> lang.name().equalsIgnoreCase(status)).findFirst().orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, String.format(
                "Movie Session Status with status [%s] was not found!", status
        )));
        entity.setStatus(sessionStatus.getId());
        movieSessionRepository.save(entity);
    }

    @Override
    public void deleteMovieSession(Long id) {

        var entity = fetchMovieSessionIfExist(id);
        entity.setStatus(MovieSessionStatus.DELETED.getId());
        movieSessionRepository.save(entity);

    }

    @Override
    public MovieSession fetchMovieSessionIfExist(Long id) {
        return movieSessionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, String.format(
                "Movie Session with id [%d] was not found!", id
        )));
    }

    private void addRelations(MovieSession entity, Long movieId, Long hallId, Long movieLanguageId, Long subtitleLanguageId) {
        if (movieId != null) {
            var movie = movieService.fetchMovieIfExist(movieId);
            entity.setMovie(movie);

            if (movieLanguageId != null) {
                var movieLanguage = movie.getLanguages().stream().filter(movieLang -> movieLang.getId() == movieLanguageId).findFirst()
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, String.format(
                                "Movie Language with id [%d] was not found!", movieLanguageId
                        )));

                entity.setLanguage(movieLanguage);
            }
            if (subtitleLanguageId != null) {
                System.out.println(movie.getSubtitleLanguages());
                var movieSubtitleLanguage = movie.getSubtitleLanguages().stream().filter(subtitleLang -> subtitleLang.getId() == subtitleLanguageId).findFirst()
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, String.format(
                                "Movie Subtitle Language with id [%d] was not found!", subtitleLanguageId
                        )));
                System.out.println(movieSubtitleLanguage);
                entity.setSubtitleLanguage(movieSubtitleLanguage);
            }
        }
        if (hallId != null) {
            var hall = hallService.fetchHallIfExist(hallId);
            entity.setHall(hall);
        }

    }
}
