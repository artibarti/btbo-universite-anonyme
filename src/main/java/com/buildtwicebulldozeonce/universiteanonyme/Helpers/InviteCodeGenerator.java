package com.buildtwicebulldozeonce.universiteanonyme.Helpers;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InviteCodeGenerator {

    public static String GenerateInviteCode()
    {
        String inviteCode = RandomStringGenerator.getNextString(6, false, true, true);
        log.info("Invite code generated: " + inviteCode);
        return inviteCode;
    }
}
