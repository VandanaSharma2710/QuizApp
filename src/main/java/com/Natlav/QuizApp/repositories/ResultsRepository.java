package com.Natlav.QuizApp.repositories;

import com.Natlav.QuizApp.entities.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultsRepository extends JpaRepository<Result, Long> {
}
