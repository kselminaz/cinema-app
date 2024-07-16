package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.model.Language;
import group.aist.cinemaapp.model.Sector;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SectorRepository extends CrudRepository<Sector, Long> {
    //    @EntityGraph(attributePaths = {"hall"})
//    Optional<Sector> findById(Long id);
//    @EntityGraph(attributePaths = {"hall"})
    Page<Sector> findAllByStatusIs(Pageable pageable, Integer status);
}
