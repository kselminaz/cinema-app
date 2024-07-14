package group.aist.cinemaapp.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor    
public enum MovieStatus {
    VISIBLE(1),INVISIBLE(2),DELETED(3);

    private final int id;
 
    public static MovieStatus findById(int id) {
        return Arrays.stream(MovieStatus.values())
                .filter(movieStatus -> movieStatus.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
