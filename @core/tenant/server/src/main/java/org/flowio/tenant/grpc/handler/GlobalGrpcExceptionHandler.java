package org.flowio.tenant.grpc.handler;


import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.flowio.tenant.exception.BaseException;

@GrpcAdvice
@Slf4j
public class GlobalGrpcExceptionHandler {
    @GrpcExceptionHandler({BaseException.class})
    public Status handleBaseException(BaseException ex) {
        log.error("A defined exception is caught.", ex);
        return ex.toRpcStatus();
    }
}
