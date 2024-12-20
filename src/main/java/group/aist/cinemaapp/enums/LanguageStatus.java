package group.aist.cinemaapp.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum LanguageStatus {

    VISIBLE(1), INVISIBLE(2), DELETED(3);

    private final int id;

    public static LanguageStatus getStatusById(int id) {
        return Arrays.stream(values())
                .filter(status -> status.getId() == id)
                .findFirst()
                .orElse(null);
    }

}
