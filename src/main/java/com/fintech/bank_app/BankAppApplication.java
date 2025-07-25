package com.fintech.bank_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BankAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankAppApplication.class, args);
	}

	@GetMapping("/ping")
	public String sayHi() {
		return "Hello World";
	}
}
