package com.buildtwicebulldozeonce.universiteanonyme.Controllers;

import com.buildtwicebulldozeonce.universiteanonyme.Models.AnonUser;
import com.buildtwicebulldozeonce.universiteanonyme.Services.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@Log
public class EncryptionController {

    private final UserService userService;

    @Autowired
    public EncryptionController(UserService userService)
    {
        this.userService = userService;
    }

    @RequestMapping(value = "/users/{userName}/getsalt", method = RequestMethod.GET)
    public String getSaltForUser(@PathVariable String userName)
    {
        log.info("Getting salt for: " + userName);
        if(!userService.isUserNameAlreadyTaken(userName))
        {
            log.info("User does not exist: " + userName);
            return "";
        }

        AnonUser anonUser = userService.getAnonUserByUserName(userName);
        String hashedPassword = anonUser.getHashedPassword();
        String salt = hashedPassword.substring(hashedPassword.lastIndexOf("#")+1);
        log.info("Returning salt for user: " + userName + "\nSalt: " + salt);
        return salt;
    }
}
