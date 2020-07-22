package com.happysolutions.erideappratingbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ERideAppRatingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ERideAppRatingBackendApplication.class, args);
	}

}
