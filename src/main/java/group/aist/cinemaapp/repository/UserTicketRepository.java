package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.model.UserTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserTicketRepository extends JpaRepository<UserTicket, Long> {

    @EntityGraph(value = "userTicketWithRelations")
    Page<UserTicket> findAllByStatusIs(Pageable pageable, Integer status);

    @EntityGraph(value = "userTicketWithRelations")
    Page<UserTicket> findAllByUserIdAndStatusIs(Pageable pageable, String userId,Integer status);

    @Override
    @EntityGraph(value = "userTicketWithRelations")
    Optional<UserTicket> findById(Long aLong);
}
