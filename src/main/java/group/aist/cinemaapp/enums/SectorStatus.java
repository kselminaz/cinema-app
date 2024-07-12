package group.aist.cinemaapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SectorStatus {
    ACTIVE(1),REPAIRED(2),DELETED(3);
    private final int id;
}
