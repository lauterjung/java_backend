package com.lauter.serverce.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	private static final String API_TITLE = "Serverce API";
	private static final String API_VERSION = "1.0.0";
	private static final String API_DESCRIPTION = "The Serverce API provides access to a platform for managing job postings, including job listings, "
			+ "application management, and company profiles. <br>"
			+ "This API allows users to browse, filter, and apply for job opportunities, while also enabling employers "
			+ "to post job openings, manage candidates, and track applications.";

	private static final String CONTACT_NAME = "Lauter";

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(buildApiInfo());
	}

	private Info buildApiInfo() {
		return new Info().title(API_TITLE).version(API_VERSION).description(API_DESCRIPTION)
				.contact(new Contact().name(CONTACT_NAME));
	}

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("public").pathsToMatch("/**").build();
	}
}
