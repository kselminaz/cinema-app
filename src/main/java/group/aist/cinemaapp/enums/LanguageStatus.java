package group.aist.cinemaapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LanguageStatus {
    VISIBLE(1),INVISIBLE(2),DELETED(3);

    private final int value;

}
