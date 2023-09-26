package ru.hogwarts_school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NegativeNumberException extends RuntimeException {
    public NegativeNumberException() {
    }

    public NegativeNumberException(String message) {
        super(message);
    }

    public NegativeNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NegativeNumberException(Throwable cause) {
        super(cause);
    }

    public NegativeNumberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
