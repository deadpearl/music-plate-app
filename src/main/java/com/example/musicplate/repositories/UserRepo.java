package com.example.musicplate.repositories;

import com.example.musicplate.models.User;

import java.util.List;

public interface UserRepo {
    User getUser(Long Id);
    void addUser(User user);
    void updateUser(Long id, User user);
    List<User> getAllUsers();
    void removeUser(Long id);
    void removeCart(Long id);
}
