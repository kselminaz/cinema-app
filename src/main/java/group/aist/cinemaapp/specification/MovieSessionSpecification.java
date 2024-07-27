package group.aist.cinemaapp.specification;

import group.aist.cinemaapp.annotation.Log;
import group.aist.cinemaapp.criteria.MovieSessionSearchCriteria;
import group.aist.cinemaapp.enums.MovieSessionStatus;
import group.aist.cinemaapp.model.Movie;
import group.aist.cinemaapp.model.MovieSession;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static jakarta.persistence.criteria.JoinType.INNER;

@AllArgsConstructor
@Log
public class MovieSessionSpecification implements Specification<MovieSession> {

    private MovieSessionSearchCriteria criteria;


    @Override
    public Predicate toPredicate(Root<MovieSession> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        var predicates = new ArrayList<Predicate>();

        if (criteria.getSearchDate() != null) {
            LocalDateTime startOfDay = criteria.getSearchDate().atStartOfDay();
            LocalDateTime endOfDay = criteria.getSearchDate().atTime(LocalTime.MAX);
            predicates.add(criteriaBuilder.between(root.get("datetime"), startOfDay, endOfDay));
        }

        if (criteria.getMovieName() != null) {

            Join<MovieSession, Movie> movieJoin = root.join("movie", INNER);
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(movieJoin.get("name")), "%" + criteria.getMovieName().toLowerCase() + "%"));
        }
        if (criteria.getStatus() != null)
            predicates.add(criteriaBuilder.equal(root.get("status"), MovieSessionStatus.valueOf(criteria.getStatus()).getId()));
        else
            predicates.add(criteriaBuilder.equal(root.get("status"), MovieSessionStatus.VISIBLE.getId()));

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

    }
}
