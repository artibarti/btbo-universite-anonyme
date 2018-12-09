package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.Models.AnonUser;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class EncryptionController {

    @RequestMapping(value = "/users/{userName}/getsalt", method = RequestMethod.GET)
    public String getSaltForUser(@PathVariable String userName) {
        log.info("Getting salt for: " + userName);
        if (!UserService.isUserNameAlreadyTaken(userName)) {
            log.info("User does not exist: " + userName);
            return "";
        }

        AnonUser anonUser = UserService.getAnonUserByUserName(userName);
        String hashedPassword = anonUser.getHashedPassword();
        String salt = hashedPassword.substring(hashedPassword.lastIndexOf("#") + 1);
        log.info("Returning salt for user: " + userName + "\nSalt: " + salt);
        return salt;
    }
}
