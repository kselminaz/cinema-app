package group.aist.cinemaapp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static group.aist.cinemaapp.exception.ErrorCode.DATA_NOT_FOUND;
import static group.aist.cinemaapp.exception.ErrorCode.SERVER_ERROR;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public List<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<ErrorResponse> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(new ErrorResponse(fieldName, errorMessage));
        });
        return errors;
    }

}
