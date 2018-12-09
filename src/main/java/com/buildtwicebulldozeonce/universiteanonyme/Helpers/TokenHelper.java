package com.buildtwicebulldozeonce.universiteanonyme.Helpers;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenHelper {
    public static String generateToken() {
        String token = RandomStringGenerator.getNextString(12, true, true, true);
        log.info("Session token generated: " + token);
        return token;
    }
}
