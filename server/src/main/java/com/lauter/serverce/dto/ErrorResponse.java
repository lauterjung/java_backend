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

	@Schema(description = "HTTP status code", example = "404")
	private int status;

	@Schema(description = "Detailed error message", example = "User not found")
	private String message;

	@Schema(description = "Time when the error occurred, in epoch milliseconds", example = "1715612345678")
	private long timestamp;

	public ErrorResponse(int status, String message) {
		this.status = status;
		this.message = message;
		this.timestamp = System.currentTimeMillis();
	}
}
