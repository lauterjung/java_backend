package com.lauter.serverce.dto.users;

import java.time.LocalDate;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "User", description = "Representation of a user")
public class UserDTO {

	@Schema(description = "The unique identifier of the user")
	private UUID id;

	@Schema(description = "The name of the user")
	private String name;

	@Schema(description = "The user's email address")
	private String email;

	@Schema(description = "The user's date of birth")
	private LocalDate dateOfBirth;

	@Schema(description = "The age of the user")
	private int age;

	public UserDTO(UUID id, String name, String email, LocalDate dateOfBirth, int age) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.age = age;
	}
}
