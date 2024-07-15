package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.model.MovieLanguage;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieLanguageRepository extends JpaRepository<MovieLanguage, Long> {

    @Override
    @EntityGraph(value = "movieLanguageWithRelations")
    List<MovieLanguage> findAll();
}
