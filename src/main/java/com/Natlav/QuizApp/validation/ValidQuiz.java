package com.Natlav.QuizApp.validation;

import jakarta.validation.Payload;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = QuizValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidQuiz {
    String message() default "Invalid Quiz";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
