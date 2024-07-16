package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.model.Seat;
import org.springframework.data.repository.CrudRepository;

public interface SeatRepository  extends CrudRepository<Seat, Long> {
}
