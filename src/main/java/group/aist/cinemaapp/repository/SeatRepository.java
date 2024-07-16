package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.model.Seat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SeatRepository  extends CrudRepository<Seat, Long> {

    @EntityGraph(value = "seatWithRelations")
    Page<Seat> findAllByStatusIs(Pageable pageable, Integer status);

    @Override
    @EntityGraph(value = "seatWithRelations")
    Optional<Seat> findById(Long Long);
}
