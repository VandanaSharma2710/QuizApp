package com.Natlav.QuizApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultResponse {
   private Long id;
   private int score;
   private int totalScore;
   private Long quizId;
}
