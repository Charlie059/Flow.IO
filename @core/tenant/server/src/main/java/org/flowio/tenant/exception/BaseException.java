package org.flowio.tenant.exception;

import com.google.rpc.Code;
import lombok.Getter;
import org.flowio.tenant.entity.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public abstract class BaseException extends RuntimeException {
    private final int code;
    private final HttpStatus status;
    private final int grpcCode;

    protected BaseException(HttpStatus status, int code, String message) {
        super(message);
        this.status = status;
        this.code = code;
        this.grpcCode = getGrpcCodeFromHttpStatus(status).getNumber();
    }

    private static Code getGrpcCodeFromHttpStatus(HttpStatus status) {
        try {
            return Code.valueOf(status.name());
        } catch (IllegalArgumentException e) {
            return Code.UNKNOWN;
        }
    }

    public Response toResponse() {
        return Response.error(code, getMessage());
    }

    public ResponseEntity<Response> toResponseEntity() {
        return new ResponseEntity<>(toResponse(), status);
    }
}
