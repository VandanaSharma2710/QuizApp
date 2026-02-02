package com.Natlav.QuizApp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QuizResponse {
    private Long id;
    private String title;
    private String description;
    private List<QuestionResponse> questions;
}
