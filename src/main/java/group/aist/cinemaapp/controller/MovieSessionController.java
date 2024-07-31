package group.aist.cinemaapp.controller;

import group.aist.cinemaapp.criteria.MovieSessionSearchCriteria;
import group.aist.cinemaapp.criteria.MovieSessionSortingCriteria;
import group.aist.cinemaapp.criteria.PageCriteria;
import group.aist.cinemaapp.dto.request.MovieSessionCreateRequest;
import group.aist.cinemaapp.dto.request.MovieSessionUpdateRequest;
import group.aist.cinemaapp.dto.response.MovieSessionResponse;
import group.aist.cinemaapp.dto.response.PageableResponse;
import group.aist.cinemaapp.service.MovieSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/movie-sessions")
public class MovieSessionController {

    private final MovieSessionService movieSessionService;

    @GetMapping("/{id}")
    public MovieSessionResponse getMovieSessionById(@PathVariable Long id) {
        return movieSessionService.getMovieSessionById(id);
    }

    @GetMapping
    public PageableResponse<MovieSessionResponse> getMovieSessions(PageCriteria pageCriteria, MovieSessionSortingCriteria sortingCriteria, MovieSessionSearchCriteria searchCriteria) {
        return movieSessionService.getMovieSessions(pageCriteria,sortingCriteria,searchCriteria);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void saveMovieSession(@RequestBody MovieSessionCreateRequest request) {
        movieSessionService.saveMovieSession(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateMovieSession(@PathVariable Long id, @RequestBody MovieSessionUpdateRequest request) {
        movieSessionService.updateMovieSession(id, request);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateMovieSessionStatus(@PathVariable Long id, @RequestParam String status) {
        movieSessionService.updateMovieSessionWithStatus(id, status);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteMovieSessionById(@PathVariable Long id) {
        movieSessionService.deleteMovieSession(id);
    }

}
