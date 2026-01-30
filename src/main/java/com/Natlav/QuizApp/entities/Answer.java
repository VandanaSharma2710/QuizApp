package com.Natlav.QuizApp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="answers")
public class Answer {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String option_answer ;
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name="question_id")
    private Question question;
}
