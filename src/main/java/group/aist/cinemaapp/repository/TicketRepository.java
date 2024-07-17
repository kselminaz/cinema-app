package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    @EntityGraph(value = "ticketWithRelations")
    Page<Ticket> findAllByStatusIs(Pageable pageable, Integer status);

    @Override
    @EntityGraph(value = "ticketWithRelations")
    Optional<Ticket> findById(Long aLong);
}
