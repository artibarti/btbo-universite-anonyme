package com.buildtwicebulldozeonce.universiteanonyme.Helpers;

import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

public class Functions
{
    public static HashMap<String, String> getValuesFromHttpHeader(HttpHeaders headers, String... keys)
    {
        HashMap<String, String> values = new HashMap<>();

        for (String key : keys)
        {
            values.put(key, headers.getFirst(key));
        }

        return values;
    }

    public static String getValueFromHttpHeader(HttpHeaders headers, String key)
    {
        return headers.getFirst(key);
    }
}
