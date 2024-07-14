package group.aist.cinemaapp.service.impl;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.MovieRequest;
import group.aist.cinemaapp.dto.response.MovieResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.MovieStatus;
import group.aist.cinemaapp.exception.NotFoundException;
import group.aist.cinemaapp.mapper.MovieMapper;
import group.aist.cinemaapp.model.Language;
import group.aist.cinemaapp.model.Movie;
import group.aist.cinemaapp.repository.MovieRepository;
import group.aist.cinemaapp.service.LanguageService;
import group.aist.cinemaapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static group.aist.cinemaapp.enums.LanguageStatus.VISIBLE;
import static group.aist.cinemaapp.enums.MovieStatus.DELETED;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.HttpStatus.BAD_REQUEST;


@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final LanguageService languageService;

    @Override
    public MovieResponse getMovieById(Long id) {
        Movie movie = getMovieIfExist(id);
        if(movie.getStatus() != MovieStatus.VISIBLE.getId()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie with id [" + id + "] is not visible.");
        }
        return movieMapper.toMovieResponse(movie);
    }

    @Override
    public PageableResponse<MovieResponse> getMovies(PageCriteria pageCriteria) {
        var resultsPage = movieRepository.findAllByStatusIs(PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount(), Sort.by(ASC, "name")), VISIBLE.getId());
        return movieMapper.toPageableResponse(resultsPage);
    }

    @Override
    public void saveMovie(MovieRequest movieRequest) {
        Movie movie = movieMapper.toMovie(movieRequest);
        movie.setStatus(MovieStatus.VISIBLE.getId());
        List<Long> subtitleLanguageIds = movieRequest.getSubtitleLanguages();
        if(!subtitleLanguageIds.isEmpty()){
            List<Language> subtitleLanguages=subtitleLanguageIds.stream().map(languageService::fetchLanguageIfExist).collect(Collectors.toList());
            movie.setSubtitleLanguages(subtitleLanguages);
        }
        movieRepository.save(movie);
    }

    @Override
    public void updateMovie(Long id, MovieRequest movieRequest) {
        Movie movie = getMovieIfExist(id);

        movie.setName(movieRequest.getName());
        movie.setImage(movieRequest.getImage());
        movie.setDescription(movieRequest.getDescription());
        movie.setReleaseTime(movieRequest.getReleaseTime());
        movie.setDuration(movieRequest.getDuration());
        movie.setAgeLimit(movieRequest.getAgeLimit());
        movieRepository.save(movie);
    }

    @Override
    public void updateMovieStatus(Long id, String status) {
        Movie movieEntity = getMovieIfExist(id);
        MovieStatus movieStatus = Arrays.stream(MovieStatus.values()).filter(movie -> movie.name().equalsIgnoreCase(status)).findFirst().orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, String.format(
                "Movie Status with status [%s] was not found!", status
        )));
        movieEntity.setStatus(movieStatus.getId());
        movieRepository.save(movieEntity);
    }

    @Override
    public void deleteMovieById(Long id) {
        Movie movie = getMovieIfExist(id);
        movie.setStatus(DELETED.getId());
        movieRepository.save(movie);
    }

    @Override
    public Movie getMovieIfExist(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(
                "Movie with id [%d] was not found!", id
        )));
    }
}
