package com.Natlav.QuizApp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="quiz_id")
    private Quiz quiz;

    private int score;

    private int totalScore;

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
}


