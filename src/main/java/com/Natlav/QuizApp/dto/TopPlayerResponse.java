package com.Natlav.QuizApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopPlayerResponse {
        private String playerName;
        private Double avgScore;
        private Double avgTotalScore;
}
