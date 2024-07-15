package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.model.Hall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface HallRepository extends JpaRepository<Hall, Long> {
    //    @EntityGraph(attributePaths = {"sector"})
//    Optional<Hall> findById(Long id);
//    @EntityGraph(attributePaths = {"sector"})
    Page<Hall> findAllByStatusIs(Pageable pageable, Integer status);
}
