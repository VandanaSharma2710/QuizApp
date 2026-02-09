package com.Natlav.QuizApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DashboardStatResponse {
    private long totalQuizzes;
    private long totalPlayers;
    private long totalAttempts;
}



