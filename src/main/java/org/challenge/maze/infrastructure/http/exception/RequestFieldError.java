package org.challenge.maze.infrastructure.http.exception;

import lombok.AllArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class RequestFieldError {
    public final String fieldName;
    public final String message;

    public static List<RequestFieldError> from(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(fieldError -> new RequestFieldError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}
