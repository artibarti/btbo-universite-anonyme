package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.AnonUser;
import com.buildtwicebulldozeonce.universiteanonyme.Models.LoggedInUser;
import com.buildtwicebulldozeonce.universiteanonyme.Models.User;
import jdk.vm.ci.meta.Local;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class LoggedInUserService {

    private static final List<LoggedInUser> loggedInUsers = new ArrayList<>();

    private static void checkUsersTimeStamps() {

        for (int i = 0; i < loggedInUsers.size(); i++) {
            if (loggedInUsers.get(i).getValidUntil().isBefore(LocalDateTime.now())) {
                loggedInUsers.remove(i);
                i--;
            }
        }

    }

    public static void addLoggedInUser(User user, AnonUser anonUser, String token) {
        loggedInUsers.add(new LoggedInUser(user,anonUser,token,LocalDateTime.now().plusMinutes(1)));
    }

    public static void removeLoggedInUser(String token) {
        loggedInUsers.remove(getLoggedInUser(token));
    }

    public static boolean logoutLoggedInUser(String token) {
        loggedInUsers.remove(getLoggedInUser(token));
        return true;
    }

    public static void extendTokenTime(String token) {
        loggedInUsers.stream()
                .filter(loggedInUser -> loggedInUser.getToken().equals(token))
                .forEach(loggedInUser -> loggedInUser.setValidUntil(LocalDateTime.now().plusMinutes(1)));
    }

    public static LoggedInUser getLoggedInUser(String token) {
        log.info("Getting logged in user");
        extendTokenTime(token);
        return loggedInUsers.stream()
                .filter(loggedInUser -> token.equals(loggedInUser.getToken()))
                .findFirst()
                .orElse(null);
    }

    public static String getTokenForUser(User user) {
        log.info("Getting token for user: "+user.getId());
        Optional<LoggedInUser> result = loggedInUsers.stream()
                .filter(loggedInUser -> loggedInUser.getUser().getId() == user.getId())
                .findFirst();
        if (result.isPresent()) {
            return result.get().getToken();
        } else {
            return "";
        }
    }

    public static String getTokenForAnonUser(AnonUser anonUser) {
        log.info("Getting token for anon: "+anonUser.getId());
        Optional<LoggedInUser> result = loggedInUsers.stream()
                .filter(loggedInUser -> loggedInUser.getAnonUser().getId() == anonUser.getId())
                .findFirst();
        if (result.isPresent()) {
            return result.get().getToken();
        } else {
            return "";
        }
    }

    public static boolean isUserLoggedIn(String token) {
        checkUsersTimeStamps();
        return getLoggedInUser(token) != null;
    }

}
