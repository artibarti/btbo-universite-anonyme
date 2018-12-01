package com.buildtwicebulldozeonce.universiteanonyme.Helpers;

import lombok.extern.java.Log;

import java.util.Random;

@Log
public class TokenHelper
{
    public static String generateToken() {
        int leftLimit = 97; // a
        int rightLimit = 132; // z
        int targetStringLength = 12;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)(random.nextFloat() * (rightLimit - leftLimit + 1));
            if(randomLimitedInt > 122)
                randomLimitedInt = randomLimitedInt - 75;
            else {
                if (random.nextBoolean()) {
                    randomLimitedInt = randomLimitedInt - 32;
                }
            }
            buffer.append((char) randomLimitedInt);
        }
        String token = buffer.toString();
        log.info("Session token generated: " + token);
        return token;
    }
}
