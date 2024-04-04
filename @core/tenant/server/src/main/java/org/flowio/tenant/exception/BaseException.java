package org.flowio.tenant.exception;

import io.grpc.Status;
import io.grpc.Status.Code;
import lombok.Getter;
import org.flowio.tenant.entity.Response;
import org.flowio.tenant.error.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Getter
public abstract class BaseException extends RuntimeException {
    private final int code;
    private final HttpStatus status;
    private final Code grpcCode;
    private final Serializable data;

    protected BaseException(HttpStatus status, int code, String message) {
        this(status, code, message, null);
    }

    protected BaseException(HttpStatus status, int code, String message, Serializable data) {
        super(message);
        this.status = status;
        this.code = code;
        this.grpcCode = getGrpcCodeFromHttpStatus(status);
        this.data = data;
    }

    protected BaseException(HttpStatus status, ResponseError responseError) {
        this(status, responseError, null);
    }

    protected BaseException(HttpStatus status, ResponseError responseError, Serializable data) {
        this(status, responseError.getCode(), responseError.getMessage(), data);
    }

    private static Code getGrpcCodeFromHttpStatus(HttpStatus status) {
        try {
            return Code.valueOf(status.name());
        } catch (IllegalArgumentException e) {
            return Code.UNKNOWN;
        }
    }

    public Response toResponse() {
        return Response.error(code, getMessage(), data);
    }

    public ResponseEntity<Response> toResponseEntity() {
        return new ResponseEntity<>(toResponse(), status);
    }

    public Status toRpcStatus() {
        return Status.fromCode(grpcCode)
            .withDescription(getMessage());
    }
}
