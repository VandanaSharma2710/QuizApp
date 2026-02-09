package com.Natlav.QuizApp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Map;

@Getter
@AllArgsConstructor
public class SubmitQuiz {

    @NotNull(message = "Answers cannot be null")
    @NotEmpty(message = "At least one answer must be submitted")
    private Map<Long, Long> answers;

    @Min(value = 1, message = "Time taken must be more than 1 second")
    private long timeTakenSeconds;
}
