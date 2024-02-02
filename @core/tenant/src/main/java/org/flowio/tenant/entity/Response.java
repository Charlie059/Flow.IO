package org.flowio.tenant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Response<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Response<T> success(T data) {
        return Response.<T>builder().code(200).message("Success!").data(data).build();
    }

    public static Response success() {
        return Response.builder().code(200).message("Success!").build();
    }

    public static Response error(Integer code, String message) {
        return Response.builder().code(code).message(message).build();
    }
}
