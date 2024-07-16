package group.aist.cinemaapp.util;

import group.aist.cinemaapp.criteria.MovieSessionSortingCriteria;
import group.aist.cinemaapp.enums.SortDirection;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SortingUtil {

    private Optional<Sort.Order> getOrder(String field, SortDirection direction) {

        return Optional.ofNullable(field)
                .flatMap(f -> Optional.ofNullable(direction)
                        .map(d -> new Sort.Order(Direction.valueOf(direction.name()), f)));

    }

    public List<Sort.Order> buildSortOrdersForMovieSessions(MovieSessionSortingCriteria sortingCriteria) {

        List<Sort.Order> orders = new ArrayList<>();

        getOrder("datetime", sortingCriteria.getDatetime()).ifPresent(orders::add);

        return orders;
    }

}
