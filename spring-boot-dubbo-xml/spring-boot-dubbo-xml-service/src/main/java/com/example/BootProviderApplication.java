package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = "classpath:dubbo-provider.xml")
public class BootProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootProviderApplication.class, args);
	}

}
