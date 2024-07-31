package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.model.MovieSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MovieSessionRepository extends JpaRepository<MovieSession, Long>, JpaSpecificationExecutor<MovieSession> {

    @EntityGraph(value = "movieSessionWithRelations")
    Page<MovieSession> findAll(Specification<MovieSession> specification, Pageable pageable);

    @Override
    @EntityGraph(value = "movieSessionWithRelations")
    Optional<MovieSession> findById(Long aLong);

}
