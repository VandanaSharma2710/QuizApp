package com.Natlav.QuizApp.repositories;

import com.Natlav.QuizApp.entities.User;
import com.Natlav.QuizApp.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsersRepository extends JpaRepository <User, Long>{
    Optional<User> findByEmail(String email);

    // Count only users with role PLAYER
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
    long countPlayers(@Param("role") RoleType role);
}
