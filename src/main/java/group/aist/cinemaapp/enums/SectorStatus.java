package group.aist.cinemaapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum SectorStatus {
    ACTIVE(1), REPAIRED(2), DELETED(3);
    private final int id;

    public static SectorStatus getSectorStatusById(int id) {
        return Arrays.stream(values())
                .filter(status -> status.getId() == id)
                .findFirst()
                .orElse(null);
    }

}
