package pl.brzezinski.web_quiz_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WebQuizServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebQuizServiceApplication.class, args);
	}

}
