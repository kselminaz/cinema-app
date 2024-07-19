package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.model.Hall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface HallRepository extends JpaRepository<Hall, Long> {
    @EntityGraph(value = "hallWithRelations")
    Optional<Hall> findById(Long id);

    @EntityGraph(value = "hallWithRelations")
    Page<Hall> findAllByStatusIs(Pageable pageable, Integer status);
}
