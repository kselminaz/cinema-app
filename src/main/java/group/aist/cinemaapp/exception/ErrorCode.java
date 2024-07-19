package group.aist.cinemaapp.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    SERVER_ERROR,
    DATA_NOT_FOUND

}
