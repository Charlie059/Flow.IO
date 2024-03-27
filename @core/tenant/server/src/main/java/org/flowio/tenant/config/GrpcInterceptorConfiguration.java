package org.flowio.tenant.config;

import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.flowio.tenant.grpc.LogGrpcInterceptor;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class GrpcInterceptorConfiguration {

    @GrpcGlobalServerInterceptor
    LogGrpcInterceptor logServerInterceptor() {
        return new LogGrpcInterceptor();
    }

}
