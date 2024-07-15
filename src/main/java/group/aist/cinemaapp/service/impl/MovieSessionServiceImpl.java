package group.aist.cinemaapp.service.impl;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.MovieSessionCreateRequest;
import group.aist.cinemaapp.dto.request.MovieSessionUpdateRequest;
import group.aist.cinemaapp.dto.response.MovieSessionResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.LanguageStatus;
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
import java.util.Set;
import java.util.stream.Collectors;

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

        var entity=fetchMovieSessionIfExist(id);
        return movieSessionMapper.toResponse(entity);
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

        var entity = fetchMovieSessionIfExist(id);
        var movieSessionStatus = Arrays.stream(MovieSessionStatus.values()).filter(sessionStatus -> sessionStatus.name().equalsIgnoreCase(status)).findFirst().orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, String.format(
                "Movie Session Status with status [%s] was not found!", status
        )));
        entity.setStatus(movieSessionStatus.getId());
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

    private void addRelations(MovieSession entity, Long movieId, Long hallId, Long langId, Long subtitleId) {
        if (movieId != null) {
            var movie = movieService.getMovieIfExist(movieId);
            entity.setMovie(movie);

            if (subtitleId != null) {
                if(movie.getSubtitleLanguages().contains(subtitleId))
                entity.setSubtitleLanguage(languageService.fetchLanguageIfExist(subtitleId));
            }
        }

    }
}
