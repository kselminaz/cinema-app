package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.dto.response.MovieResponse;
import group.aist.cinemaapp.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @EntityGraph(value = "movieWithLanguages")
    @Query("SELECT m FROM Movie m WHERE " +
            "LOWER(m.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(m.description) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<Movie> findMoviesBySearchText(@Param("searchText") String searchText);

    @EntityGraph(value = "movieWithLanguages")
    Page<Movie> findAllByStatusIs(Pageable pageable, Integer status);

    @Override
    @EntityGraph(value = "movieWithLanguages")
    Optional<Movie> findById(Long aLong);
}
