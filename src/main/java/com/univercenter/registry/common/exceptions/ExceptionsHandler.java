package com.univercenter.registry.common.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(final NotFoundException exception) {
        return responseBody(NOT_FOUND, exception);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(final BadRequestException exception) {
        return responseBody(BAD_REQUEST, exception);
    }

    /**
     * This method does handle all those exceptions that are not
     * already handled in {@link ResponseEntityExceptionHandler}.
     *
     * @param exception to be handled.
     * @return ResponseEntity with status code and no body.
     * @see ResponseEntityExceptionHandler#handleException(Exception, WebRequest)
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleException(final Exception exception) {
        return responseBody(INTERNAL_SERVER_ERROR, exception);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            final Exception ex,
            final Object body,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request
    ) {
        log.error(ex.getMessage(), ex);
        if (request instanceof ServletWebRequest servletWebRequest) {
            final var response = servletWebRequest.getResponse();
            if (response != null && response.isCommitted()) {
                if (log.isWarnEnabled()) {
                    log.warn("Response already committed. Ignoring: ", ex);
                }
                return null;
            }
        }
        if(ex instanceof HttpMessageNotReadableException) {
            final var message = new Error("Request body not provided or invalid");
            return ResponseEntity.status(status).headers(headers).body(message);
        }
        final var message = new Error("Error processing the request");
        return ResponseEntity.status(status).headers(headers).body(message);
    }

    @Override
    protected ResponseEntity<Object> createResponseEntity(
            final Object body,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request
    ) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    private ResponseEntity<?> responseBody(
            final HttpStatus status,
            final Exception exception
    ) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(status).body(new Error(exception.getMessage()));
    }

    private record Error(String message){}
}
