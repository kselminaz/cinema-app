package group.aist.cinemaapp.controller;

import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.MovieRequest;
import group.aist.cinemaapp.dto.response.MovieResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public PageableResponse<MovieResponse> getMovies(PageCriteria pageCriteria){
        return movieService.getMovies(pageCriteria);
    }
    @PostMapping
    public void saveMovie(@RequestBody MovieRequest movieRequest){
        movieService.saveMovie(movieRequest);
    }
    @PutMapping("/{id}")
    public void updateMovie(@PathVariable Long id,@RequestBody MovieRequest movieRequest){
        movieService.updateMovie(id, movieRequest);
    }
    @PatchMapping("/{id}")
    public void updateMovieStatus(@PathVariable Long id,@RequestParam String status){
        movieService.updateMovieStatus(id, status);
    }
    @DeleteMapping("{id}")
    public void deleteMovieById(@PathVariable Long id){
        movieService.deleteMovieById(id);
    }

}
