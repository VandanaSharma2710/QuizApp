package com.Natlav.QuizApp.security;

import com.Natlav.QuizApp.entities.User;
import com.Natlav.QuizApp.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final UsersRepository usersRepository;


    public User getCurrentUser(Authentication authentication) {
        System.out.println("6 SecurityUtil");
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Unauthenticated");
        }

        if (authentication.getPrincipal() instanceof User user) {
            return user;
        }
        throw new RuntimeException("Invalid Principal");

    }
}
