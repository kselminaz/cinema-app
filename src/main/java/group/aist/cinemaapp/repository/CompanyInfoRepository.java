package group.aist.cinemaapp.repository;

import group.aist.cinemaapp.model.CompanyInfo;
import group.aist.cinemaapp.model.MovieSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyInfoRepository extends JpaRepository<CompanyInfo,Long> {


}
