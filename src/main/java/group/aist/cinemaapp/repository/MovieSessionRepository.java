package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.model.MovieSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MovieSessionRepository extends CrudRepository<MovieSession, Long> , JpaSpecificationExecutor<MovieSession> {

/*
    @EntityGraph(value = "movieSessionWithRelations")
    Page<MovieSession> findAllByStatusIs(Specification<MovieSession> specification,Pageable pageable, Integer status);
*/

    @EntityGraph(value = "movieSessionWithRelations")
    Page<MovieSession> findAllByStatusIs(Pageable pageable, Integer status);


    @Override
    @EntityGraph(value = "movieSessionWithRelations")
    Optional<MovieSession> findById(Long aLong);

}
