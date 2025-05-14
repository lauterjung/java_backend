package com.lauter.serverce.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Standard error response returned when an API call fails")
public class ErrorResponse {

	@Schema(description = "HTTP status code")
	private int status;

	@Schema(description = "Detailed error message", example = "Detailed error message")
	private String message;
}
