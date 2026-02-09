package com.Natlav.QuizApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuizPerformanceResponse {
    private String quizTitle;
    private Long attempts;
    private Double avgScore;
}
