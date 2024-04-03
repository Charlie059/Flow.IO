package org.flowio.tenant.handler;

import lombok.extern.slf4j.Slf4j;
import org.flowio.tenant.entity.Response;
import org.flowio.tenant.error.ResponseError;
import org.flowio.tenant.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({BaseException.class})
    public ResponseEntity<Response> handleBaseException(BaseException ex) {
        return ex.toResponseEntity();
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Response> handleInternalError(RuntimeException ex) {
        log.error("Internal server error", ex);
        return new ResponseEntity<>(Response.error(ResponseError.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    protected ResponseEntity<Response> handleBadRequest(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(Response.error(ResponseError.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<Response> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(Response.error(400, ex.getBindingResult().getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoResourceFoundException.class, HttpRequestMethodNotSupportedException.class})
    protected ResponseEntity<Object> handleNotFound(Exception ex) {
        return new ResponseEntity<>(Response.error(ResponseError.ROUTE_NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AccessDeniedException.class})
    protected ResponseEntity<Object> handleUnauthorized(AccessDeniedException ex) {
        return new ResponseEntity<>(Response.error(ResponseError.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }
}

