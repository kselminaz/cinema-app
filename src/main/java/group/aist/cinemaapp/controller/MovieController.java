package group.aist.cinemaapp.controller;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.MovieCreateRequest;
import group.aist.cinemaapp.dto.request.MovieUpdateRequest;
import group.aist.cinemaapp.dto.response.MovieResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/movies")
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/{id}")
    public MovieResponse getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @GetMapping
    public PageableResponse<MovieResponse> getMovies(PageCriteria pageCriteria) {
        return movieService.getMovies(pageCriteria);
    }

    @PostMapping
    public void saveMovie(@RequestPart("data") MovieCreateRequest movieCreateRequest, @RequestPart("file") MultipartFile file) {
        movieService.saveMovie(movieCreateRequest,file);
    }

    @PutMapping("/{id}")
    public void updateMovie(@PathVariable Long id, @RequestBody MovieUpdateRequest movieUpdateRequest) {
        movieService.updateMovie(id, movieUpdateRequest);
    }

    @PatchMapping("/{id}")
    public void updateMovieStatus(@PathVariable Long id, @RequestParam String status) {
        movieService.updateMovieStatus(id, status);
    }

    @DeleteMapping("{id}")
    public void deleteMovieById(@PathVariable Long id) {
        movieService.deleteMovieById(id);
    }

}
