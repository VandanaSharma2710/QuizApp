package com.Natlav.QuizApp.entities;

import com.Natlav.QuizApp.validation.ValidQuiz;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "quizzes")
@ValidQuiz
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    private  String description;

    @Column(name = "time_limit_seconds", nullable = false)
    private Integer timeLimitSeconds;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy ;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @Valid
    private List<Question> questions = new ArrayList<Question>();
}
