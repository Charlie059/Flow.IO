package org.flowio.tenant.exception;

import io.grpc.Status;
import io.grpc.StatusException;
import lombok.Getter;
import org.flowio.tenant.entity.Response;
import org.flowio.tenant.error.ResponseError;
import org.flowio.tenant.utils.GrpcUtils;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Getter
public abstract class BaseException extends RuntimeException {
    private final ResponseError error;
    private final Map<String, String> data;

    protected BaseException(ResponseError error) {
        this(error, null);
    }

    protected BaseException(ResponseError error, Map<String, String> data) {
        super(error.getMessage());
        this.error = error;
        this.data = data;
    }

    public Response toResponse() {
        return Response.error(error.getCode(), getMessage(), data);
    }

    public ResponseEntity<Response> toResponseEntity() {
        return new ResponseEntity<>(toResponse(), error.getHttpStatus());
    }

    public StatusException toRpcStatusException() {
        return Status.fromCode(error.getGrpcCode())
            .withDescription(getMessage())
            .asException(GrpcUtils.mapToMetadata(data));
    }
}
