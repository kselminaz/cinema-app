package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.model.MovieSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MovieSessionRepository extends CrudRepository<MovieSession, Long> {

    @EntityGraph(value = "movieSessionWithRelations")
    Page<MovieSession> findAllByStatusIs(Pageable pageable, Integer status);

    @Override
    @EntityGraph(value = "movieSessionWithRelations")
    Optional<MovieSession> findById(Long aLong);
}
