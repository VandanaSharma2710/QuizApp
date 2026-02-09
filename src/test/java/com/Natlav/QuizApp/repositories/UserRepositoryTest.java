package com.Natlav.QuizApp.repositories;

import com.Natlav.QuizApp.entities.User;
import com.Natlav.QuizApp.enums.RoleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJdbcTest
public class UserRepositoryTest {

    @Autowired
    private  UsersRepository usersRepository;

    @Test
    void testSaveAndFindUser(){

        User user = new User();
        user.setEmail("bhavikarana2412@gmail.com");
        user.setPassword("Bhavika@24");
        user.setRole(RoleType.ROLE_PLAYER);
        user.setProvider("Google");
        usersRepository.save(user);

       Optional<User> userInfoByEmail = usersRepository.findByEmail("bhavikarana2412@gmail.com");
        assertTrue(userInfoByEmail.isPresent());
        assertEquals("bhavikarana2412@gmail.com", userInfoByEmail.get().getEmail());
    }

    @Test
    void testNonExistingUser(){
        Optional<User> nonExistingUser = usersRepository.findByEmail("shravanmalli35@gmail.com");
        assertTrue(nonExistingUser.isEmpty());
    }

}
