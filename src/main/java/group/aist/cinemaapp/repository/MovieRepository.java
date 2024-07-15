package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @EntityGraph(value = "movieWithLanguages")
    Page<Movie> findAllByStatusIs(Pageable pageable, Integer status);

    @Override
    @EntityGraph(value = "movieWithLanguages")
    Optional<Movie> findById(Long aLong);
}
