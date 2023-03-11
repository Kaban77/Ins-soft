package ru.demidov.insSoft.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "ru.demidov.*")
public class InsSoftApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsSoftApplication.class, args);
	}
}
