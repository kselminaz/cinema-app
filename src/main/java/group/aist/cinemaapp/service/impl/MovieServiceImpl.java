package group.aist.cinemaapp.service.impl;

import group.aist.cinemaapp.annotation.Log;
import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.MovieCreateRequest;
import group.aist.cinemaapp.dto.request.MovieLanguageRequest;
import group.aist.cinemaapp.dto.request.MovieUpdateRequest;
import group.aist.cinemaapp.dto.response.MovieResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.enums.MovieStatus;
import group.aist.cinemaapp.mapper.MovieMapper;
import group.aist.cinemaapp.model.Language;
import group.aist.cinemaapp.model.Movie;
import group.aist.cinemaapp.model.MovieLanguage;
import group.aist.cinemaapp.repository.MovieRepository;
import group.aist.cinemaapp.service.LanguageService;
import group.aist.cinemaapp.service.MovieService;
import group.aist.cinemaapp.util.ImageSaveUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static group.aist.cinemaapp.enums.LanguageStatus.VISIBLE;
import static group.aist.cinemaapp.enums.MovieStatus.DELETED;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@Service
@RequiredArgsConstructor
@Log
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final LanguageService languageService;
    private final ImageSaveUtil imageSaveUtil;

    @Override
    public MovieResponse getMovieById(Long id) {
        Movie movie = fetchMovieIfExist(id);
        if (movie.getStatus() != MovieStatus.VISIBLE.getId()) {
            throw new ResponseStatusException(NOT_FOUND, "Movie with id [" + id + "] is not visible.");
        }
        return movieMapper.toMovieResponse(movie);
    }

    @Override
    public PageableResponse<MovieResponse> getMovies(PageCriteria pageCriteria) {
        var resultsPage = movieRepository.findAllByStatusIs(PageRequest.of(pageCriteria.getPage(), pageCriteria.getCount()), VISIBLE.getId());
        return movieMapper.toPageableResponse(resultsPage);
    }

    @Override
    public void saveMovie(MovieCreateRequest movieCreateRequest, MultipartFile file) {
        Movie movie = movieMapper.toMovie(movieCreateRequest);
        movie.setStatus(MovieStatus.VISIBLE.getId());
        try {
            movie.setImage(imageSaveUtil.saveImage(movie.getName(),file,"movies"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addRelations(movie, movieCreateRequest.getSubtitleLanguages(), movieCreateRequest.getLanguages());
        movieRepository.save(movie);
    }

    @Override
    public void updateMovie(Long id, MovieUpdateRequest movieUpdateRequest) {
        Movie movie = fetchMovieIfExist(id);
        ofNullable(movieUpdateRequest.getName()).ifPresent(movie::setName);
        ofNullable(movieUpdateRequest.getDescription()).ifPresent(movie::setDescription);
        ofNullable(movieUpdateRequest.getReleaseTime()).ifPresent(movie::setReleaseTime);
        ofNullable(movieUpdateRequest.getDuration()).ifPresent(movie::setDuration);
        ofNullable(movieUpdateRequest.getAgeLimit()).ifPresent(movie::setAgeLimit);
        ofNullable(movieUpdateRequest.getStatus()).ifPresent(status -> movie.setStatus(MovieStatus.valueOf(movieUpdateRequest.getStatus()).getId()));
        addRelations(movie, movieUpdateRequest.getSubtitleLanguages(), movieUpdateRequest.getLanguages());
        movieRepository.save(movie);
    }

    @Override
    public void updateMovieStatus(Long id, String status) {
        Movie movieEntity = fetchMovieIfExist(id);
        MovieStatus movieStatus = Arrays.stream(MovieStatus.values()).filter(movie -> movie.name().equalsIgnoreCase(status)).findFirst().orElseThrow(() -> new ResponseStatusException(BAD_REQUEST, String.format(
                "Movie Status with status [%s] was not found!", status
        )));
        movieEntity.setStatus(movieStatus.getId());
        movieRepository.save(movieEntity);
    }

    @Override
    public void deleteMovieById(Long id) {
        Movie movie = fetchMovieIfExist(id);
        movie.setStatus(DELETED.getId());
        movieRepository.save(movie);
    }

    @Override
    public MovieLanguage addMovieLanguage(Movie movie, MovieLanguageRequest request) {
        var movieLanguage = MovieLanguage.builder()
                .movie(movie)
                .language(languageService.fetchLanguageIfExist(request.getLanguageId()))
                .isMain(request.getIsMain())
                .build();
        return movieLanguage;
    }

    @Override
    public Movie fetchMovieIfExist(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Movie with id [" + id + "] is not visible."));
    }

    public void addRelations(Movie movie, List<Long> subtitleLanguageIds, List<MovieLanguageRequest> movieLanguages) {
        if (!subtitleLanguageIds.isEmpty()) {
            Set<Language> subtitleLanguages = subtitleLanguageIds.stream().map(languageService::fetchLanguageIfExist).collect(Collectors.toSet());
            movie.setSubtitleLanguages(subtitleLanguages);
        }
        if (!movieLanguages.isEmpty()) {
            Set<MovieLanguage> languages = movieLanguages.stream().map(e -> addMovieLanguage(movie, e)).collect(Collectors.toSet());
            movie.setLanguages(languages);
        }
    }
}
