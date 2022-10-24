package org.challenge.maze.infrastructure.http.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class ApiError {
    public final String title;
    public final String message;
    public final HttpStatus status;
    public final List<RequestFieldError> fieldErrors;

    public ApiError(String title, String message, HttpStatus httpStatus) {
        this(title, message, httpStatus, Collections.emptyList());
    }
}
