package com.bap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title= "THE BANK APPLICATION API ",
		description = "Backend Rest API  for BANK APP",
		version="v1.0",
		contact= @Contact(
			name = "Banjoko Abiodun Lateef",
			email = "biodun.banjoko@yahoo.com",
			url = "https://github.com/Habay88/BANKINGAPP"
		),
		license = @License(
			name = "habay_inc",
			url="https://github.com/Habay88/BANKINGAPP"
		)
),
externalDocs = @ExternalDocumentation(
	description = "Bank App Documentation",
	url = "https://github.com/Habay88/BANKINGAPP"
)
)
public class BankingappApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingappApplication.class, args);
	}

}
