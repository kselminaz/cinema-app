package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.model.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LanguageRepository extends JpaRepository<Language,Long> {

    Page<Language> findAllByStatusIs(Pageable pageable, Integer status);
}
