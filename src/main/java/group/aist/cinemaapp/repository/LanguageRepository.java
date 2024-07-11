package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.enums.LanguageStatus;
import group.aist.cinemaapp.model.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface LanguageRepository extends CrudRepository<Language,Long> {

    Page<Language> findAllByStatusIs(Pageable pageable, Integer status);
}
