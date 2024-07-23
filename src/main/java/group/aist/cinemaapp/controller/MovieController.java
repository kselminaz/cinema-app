package group.aist.cinemaapp.controller;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.MovieCreateRequest;
import group.aist.cinemaapp.dto.request.MovieUpdateRequest;
import group.aist.cinemaapp.dto.response.MovieResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = "multipart/form-data")
    public void saveMovie(@RequestPart("data") MovieCreateRequest movieCreateRequest, @RequestPart("file") MultipartFile file) {
        movieService.saveMovie(movieCreateRequest,file);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public void updateMovie(@PathVariable Long id, @RequestBody MovieUpdateRequest movieUpdateRequest) {
        movieService.updateMovie(id, movieUpdateRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    public void updateMovieStatus(@PathVariable Long id, @RequestParam String status) {
        movieService.updateMovieStatus(id, status);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{id}")
    public void deleteMovieById(@PathVariable Long id) {
        movieService.deleteMovieById(id);
    }

}
