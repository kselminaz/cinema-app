package group.aist.cinemaapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum TicketStatus {

    ACTIVE(1), INACTIVE(2), SOLD(3),DELETED(4);

    private final int id;

    public static TicketStatus getStatusById(int id) {
        return Arrays.stream(values())
                .filter(status -> status.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
