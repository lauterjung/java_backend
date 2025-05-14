package com.lauter.serverce.dto.users;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lauter.serverce.enums.users.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CreateUser", description = "Representation of a user")
public class CreateUserDTO {
	@Schema(description = "The name of the user")
	private String name;

	@Schema(description = "The user's email address")
	private String email;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Schema(description = "The user's date of birth")
	private LocalDate dateOfBirth;

	@Schema(description = "The user's phone number")
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Schema(description = "The gender of the user", defaultValue = "MALE | FEMALE | OTHER")
	private Gender gender;
}
