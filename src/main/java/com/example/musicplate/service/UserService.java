package com.example.musicplate.service;

import com.example.musicplate.models.User;
import com.example.musicplate.repositories.UserRepo;

import java.util.List;
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User getUser(Long id) {
        return userRepo.getUser(id);
    }

    public void addUser(User user) {
        userRepo.addUser(user);
    }

    public List<User> getAllUsers() {
        return userRepo.getAllUsers();
    }

    public void removeUser(Long id) {
        userRepo.removeUser(id);
    }

    public void removeCart(Long id) {
        userRepo.removeCart(id);
    }

    public void updateUser(Long id, User user) {
        userRepo.updateUser(id, user);
    }
}
