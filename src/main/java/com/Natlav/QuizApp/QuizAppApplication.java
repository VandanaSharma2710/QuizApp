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
                "GOOGLE_CLIENT_ID", dotenv.get("GOOGLE_CLIENT_ID"),
                "GOOGLE_CLIENT_SECRET", dotenv.get("GOOGLE_CLIENT_SECRET")
        ));
        app.run(args);

	}

}
