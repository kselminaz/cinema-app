package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    @EntityGraph(value = "userWithRelations")
    Optional<User> findById(Long aLong);
}
