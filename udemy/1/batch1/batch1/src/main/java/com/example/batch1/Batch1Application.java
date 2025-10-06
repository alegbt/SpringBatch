package com.example.batch1;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class Batch1Application {

	public static void main(String[] args) {
		SpringApplication.run(Batch1Application.class, args);
	}

}
