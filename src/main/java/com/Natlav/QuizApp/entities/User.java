package com.Natlav.QuizApp.entities;

import com.Natlav.QuizApp.enums.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "users")
public class User {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String username;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = true)
    private String password;

    private String provider; //Google/GitHub for OAuth
    private  boolean isActive = true;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
    private RoleType role;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quiz> createdQuizzes = new ArrayList<Quiz>();
}
