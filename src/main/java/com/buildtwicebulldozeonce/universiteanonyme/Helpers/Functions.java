package com.buildtwicebulldozeonce.universiteanonyme.Helpers;

import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;

@Log
public class Functions {
    public static HashMap<String, String> getValuesFromHttpHeader(HttpHeaders headers, String... keys) {
        log.info("Reading http header...");

        HashMap<String, String> values = new HashMap<>();

        for (String key : keys) {
            String value = headers.getFirst(key);
            if (!"token".equals(key)) {

                log.info(String.format("Readin value for key %s... value => %s", key, value));
            }
            values.put(key, value);
        }

        return values;
    }

    public static String getValueFromHttpHeader(HttpHeaders headers, String key) {
        if (!"token".equals(key)) {
            log.info("Reading http header...");
        }
        String value = headers.getFirst(key);
        if (!"token".equals(key)) {
            log.info(String.format("Readin value for key %s... value => %s", key, value));
        }
        return value;
    }
}
