package group.aist.cinemaapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;


@Getter
@RequiredArgsConstructor
public enum SeatStatus {
    AVAILABLE(1),BOOKED(2),DELETED(3);

    private final int id;

    public static SeatStatus findById(int id) {
        return Arrays.stream(SeatStatus.values())
                .filter(seatStatus -> seatStatus.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
