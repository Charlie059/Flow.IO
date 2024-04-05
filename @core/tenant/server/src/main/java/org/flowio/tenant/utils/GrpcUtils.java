package org.flowio.tenant.utils;

import io.grpc.Metadata;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@UtilityClass
@Slf4j
public class GrpcUtils {
    public static Metadata mapToMetadata(Map<String, String> map) {
        Metadata metadata = new Metadata();
        if (map == null) {
            return metadata;
        }
        for (var entry : map.entrySet()) {
            Metadata.Key<String> key = Metadata.Key.of(camelToKebab(entry.getKey()), Metadata.ASCII_STRING_MARSHALLER);
            metadata.put(key, entry.getValue());
        }
        return metadata;
    }

    private static String camelToKebab(String input) {
        return input.replaceAll("([a-z0-9]|(?=[A-Z]))([A-Z])", "$1-$2").toLowerCase();
    }
}
