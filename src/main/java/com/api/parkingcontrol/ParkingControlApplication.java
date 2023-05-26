package com.api.parkingcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ParkingControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingControlApplication.class, args);
	}

	// GetMapping -> GET (HTTP Method) ( / -> Root Path)
	@GetMapping("/") // http://localhost:8080/ -> Hello World (Browser) or {"message":"Hello World"} (Postman)
	public String index() {
		return "Hello World";
	}
}
