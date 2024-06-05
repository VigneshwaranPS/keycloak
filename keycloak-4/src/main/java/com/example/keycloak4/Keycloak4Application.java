package com.example.keycloak4;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(
		name = "Keycloak",
		openIdConnectUrl = "http://127.0.0.1:8081/realms/Organisation2/.well-known/openid-configuration",
		scheme = "bearer",
		type = SecuritySchemeType.OPENIDCONNECT,
		in = SecuritySchemeIn.HEADER
)
public class Keycloak4Application {


	public static void main(String[] args) {

		System.out.println("\n\n----------------Main------------------\n\n");
		SpringApplication.run(Keycloak4Application.class, args);
	}

}
