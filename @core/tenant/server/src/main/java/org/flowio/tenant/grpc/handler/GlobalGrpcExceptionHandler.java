package org.flowio.tenant.grpc.handler;


import com.google.rpc.Status;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import org.flowio.tenant.exception.BaseException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@GrpcAdvice
public class GlobalGrpcExceptionHandler {
    @ExceptionHandler({BaseException.class})
    public Status handleBaseException(BaseException ex) {
        return ex.toRpcStatus();
    }
}
