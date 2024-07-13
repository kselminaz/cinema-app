package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie,Long> {
    Page<Movie> findAllByStatusIs(Pageable pageable, Integer status);
}
