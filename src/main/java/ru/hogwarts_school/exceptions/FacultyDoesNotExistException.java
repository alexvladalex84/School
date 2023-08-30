package ru.hogwarts_school.exceptions;

public class FacultyDoesNotExistException extends RuntimeException {
    public FacultyDoesNotExistException() {
    }

    public FacultyDoesNotExistException(String message) {
        super(message);
    }

    public FacultyDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public FacultyDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public FacultyDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
