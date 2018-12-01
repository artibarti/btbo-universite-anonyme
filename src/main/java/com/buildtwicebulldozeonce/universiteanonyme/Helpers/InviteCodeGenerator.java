package com.buildtwicebulldozeonce.universiteanonyme.Helpers;

import lombok.extern.java.Log;

import java.util.Random;

@Log
public class InviteCodeGenerator {
    public static String GenerateInviteCode()
    {
        int leftLimit = 65; // A
        int rightLimit = 100; // Z
        int targetStringLength = 6;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)(random.nextFloat() * (rightLimit - leftLimit + 1));
            if(randomLimitedInt > 90)
                randomLimitedInt = randomLimitedInt - 43;
            buffer.append((char) randomLimitedInt);
        }
        String inviteCode = buffer.toString();
        log.info("Invite code generated: " + inviteCode);
        return inviteCode;
    }
}
