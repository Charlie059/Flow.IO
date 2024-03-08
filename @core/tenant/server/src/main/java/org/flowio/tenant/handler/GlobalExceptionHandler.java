package org.flowio.tenant.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.flowio.tenant.entity.Response;
import org.flowio.tenant.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({BaseException.class})
    public ResponseEntity<Response> handleBaseException(HttpServletRequest request, Exception ex) {
        return ((BaseException) ex).toResponseEntity();
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Response> handleInternalError(HttpServletRequest request, Exception ex) {
        return new ResponseEntity<>(Response.error(500, "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    protected ResponseEntity<Object> handleBadRequest(HttpServletRequest request, Exception ex) {
        return new ResponseEntity<>(Response.error(400, "Bad request"), HttpStatus.BAD_REQUEST);
    }

// TODO: this exception only exists on spring boot 3.2+
//    @ExceptionHandler({NoResourceFoundException.class})
//    protected ResponseEntity<Object> handleNotFound(HttpServletRequest request, Exception ex) {
//        return new ResponseEntity<>(Response.error(404, "Route not found"), HttpStatus.NOT_FOUND);
//    }
}

