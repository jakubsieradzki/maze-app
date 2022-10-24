package org.challenge.maze.infrastructure.http.exception;

import org.challenge.maze.domain.exception.*;
import org.challenge.maze.infrastructure.db.exception.MazeDataCorruptedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Provided payload is invalid",
                HttpStatus.BAD_REQUEST,
                RequestFieldError.from(ex.getFieldErrors())
        );
        return toResponse(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
        return toResponse(apiError);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    protected ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                HttpStatus.CONFLICT
        );

        return toResponse(apiError);
    }

    @ExceptionHandler(InvalidMazeFormatException.class)
    protected ResponseEntity<Object> handleMazeDataInvalidFormatException(InvalidMazeFormatException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Provided maze has invalid format: " + ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );

        return toResponse(apiError);
    }

    @ExceptionHandler(InvalidMazeDataException.class)
    protected ResponseEntity<Object> handleInvalidMazeFormatException(InvalidMazeDataException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Provided maze has invalid data: " + ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );

        return toResponse(apiError);
    }

    @ExceptionHandler(MazeNotFoundException.class)
    protected ResponseEntity<Object> handleMazeNotFoundException(MazeNotFoundException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );

        return toResponse(apiError);
    }

    @ExceptionHandler(SolverFailedException.class)
    protected ResponseEntity<Object> handleMazeDataInvalidFormatException(SolverFailedException ex) {
        ApiError apiError = new ApiError(
                "SOLVER EXCEPTION",
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        return toResponse(apiError);
    }

    @ExceptionHandler(MazeDataCorruptedException.class)
    protected ResponseEntity<Object> handleMazeDataCorruptedException(MazeDataCorruptedException ex) {
        ApiError apiError = new ApiError(
                "DATA CORRUPTED",
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        return toResponse(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Failed to convert request parameter: " + ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
        return toResponse(apiError);
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<Object> handleConversionFailed(RuntimeException exception) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Failed to convert: " + exception.getMessage(),
                HttpStatus.BAD_REQUEST
        );
        return toResponse(apiError);
    }

    private ResponseEntity<Object> toResponse(ApiError apiError) {
        return ResponseEntity
                .status(apiError.status)
                .body(apiError);
    }

}
