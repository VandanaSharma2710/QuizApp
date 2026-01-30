package com.Natlav.QuizApp.services;

import com.Natlav.QuizApp.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {


    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    User registerUser(User user);

    User updateUser(Long id, User updatedUser);

    void deactivateUser(Long id);
}
