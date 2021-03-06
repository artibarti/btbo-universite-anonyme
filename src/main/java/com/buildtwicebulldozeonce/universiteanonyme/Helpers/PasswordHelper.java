package com.buildtwicebulldozeonce.universiteanonyme.Helpers;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Slf4j
public class PasswordHelper {
    /**
     * Compares the given hash and a passwords hash.
     *
     * @param hash     Hashed password from the database.
     * @param password The password which hash will be compared to the hash.
     * @return Returns true if the compared hashes match.
     */
    public static boolean comparePassword(String hash, String password) {
        String salt = hash.substring(hash.lastIndexOf("#") + 1);
        String hashedPassword = hashPassword(password, salt) + "#" + salt;
        return hash.equals(hashedPassword);
    }

    /**
     * Hashes a given password with the given salt.
     * If the salt is empty, the function will generate one.
     *
     * @param password The password which will be hashed.
     * @param salt     The salt for the hashing.
     * @return Returns the hashed password.
     */
    public static String hashPassword(String password, String salt) {
        String randomSalt = "";
        if (salt.isEmpty())
            randomSalt = UUID.randomUUID().toString().subSequence(0, 8).toString();
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            log.warn("Can't create SHA-256 algorithm");
        }

        if (!randomSalt.isEmpty()) {
            byte[] hash = digest.digest((password + randomSalt).getBytes(StandardCharsets.UTF_8));
            String encoded = Base64.getEncoder().encodeToString(hash);
            return encoded + "#" + randomSalt;
        } else {
            byte[] hash = digest.digest((password + salt).getBytes(StandardCharsets.UTF_8));
            String encoded = Base64.getEncoder().encodeToString(hash);
            return encoded;
        }
    }
}

