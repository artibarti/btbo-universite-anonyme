package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.User;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUser() {
        List<User> allUsers = new ArrayList<>();
        userRepository.findAll()
                .forEach(allUsers::add);

        return allUsers;
    }

    public User getUser(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }
}
