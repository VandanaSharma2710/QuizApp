package com.Natlav.QuizApp;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class QuizAppApplication {

	public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();

        SpringApplication app = new SpringApplication(QuizAppApplication.class);
        app.setDefaultProperties(Map.of(
                "DB_URL", dotenv.get("DB_URL"),
                "DB_USERNAME", dotenv.get("DB_USERNAME"),
                "DB_PASSWORD", dotenv.get("DB_PASSWORD"),
                "GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID"),
                "GOOGLE_CLIENT_SECRET", dotenv.get("GOOGLE_CLIENT_SECRET")
        ));
        app.run(args);

	}

}
