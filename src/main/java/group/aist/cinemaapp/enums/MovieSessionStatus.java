package group.aist.cinemaapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum MovieSessionStatus {

    VISIBLE(1),INVISIBLE(2),DELETED(3);


    private final int id;

    public static MovieSessionStatus getStatusById(int id) {
        return Arrays.stream(values())
                .filter(status -> status.getId() == id)
                .findFirst()
                .orElse(null);
    }

}
