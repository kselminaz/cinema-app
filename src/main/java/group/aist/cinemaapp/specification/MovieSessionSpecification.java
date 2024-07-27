package group.aist.cinemaapp.specification;

import group.aist.cinemaapp.annotation.Log;
import group.aist.cinemaapp.criteria.MovieSessionSearchCriteria;
import group.aist.cinemaapp.enums.MovieSessionStatus;
import group.aist.cinemaapp.model.Movie;
import group.aist.cinemaapp.model.MovieSession;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

import static jakarta.persistence.criteria.JoinType.INNER;

@AllArgsConstructor
@Log
public class MovieSessionSpecification implements Specification<MovieSession> {

    private MovieSessionSearchCriteria criteria;


    @Override
    public Predicate toPredicate(Root<MovieSession> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        var predicates = new ArrayList<Predicate>();

        if (criteria.getSearchDateTime() != null)
            predicates.add(criteriaBuilder.equal(root.get("dateTime"), criteria.getSearchDateTime()));

        if (criteria.getMovieName() != null) {

            Join<MovieSession, Movie> movieJoin = root.join("movie", INNER);
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(movieJoin.get("name")), "%" + criteria.getMovieName().toLowerCase() + "%"));
        }
        if (criteria.getStatus() != null)
            predicates.add(criteriaBuilder.equal(root.get("status"), MovieSessionStatus.valueOf(criteria.getStatus()).getId()));


       /* if (criteria.getSearchDateTime() != null) {
            return criteriaBuilder.conjunction();
        } else
            predicates.add(criteriaBuilder.equal(root.get("dateTime"), criteria.getSearchDateTime()));

        if (criteria.getMovieName() == null) {
            return criteriaBuilder.conjunction();
        } else {
            Join<MovieSession, Movie> movieJoin = root.join("movie", INNER);

            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(movieJoin.get("name")), "%" + criteria.getMovieName().toLowerCase() + "%"));
        }
        if (criteria.getStatus() == null) {
            return criteriaBuilder.conjunction();
        } else
            predicates.add(criteriaBuilder.equal(root.get("status"), MovieSessionStatus.valueOf(criteria.getStatus()).getId()));
*/

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

    }
}
