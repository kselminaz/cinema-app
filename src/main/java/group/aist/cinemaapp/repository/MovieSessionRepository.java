package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.model.MovieSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface MovieSessionRepository extends CrudRepository<MovieSession,Long> {

    Page<MovieSession> findAllByStatusIs(Pageable pageable, Integer status);
}
