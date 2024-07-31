package group.aist.cinemaapp.criteria;

import group.aist.cinemaapp.enums.SortDirection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieSessionSortingCriteria {

    private SortDirection sortDateTime;

}
