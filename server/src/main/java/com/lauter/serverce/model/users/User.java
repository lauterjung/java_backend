package com.lauter.serverce.model.users;

import java.time.LocalDate;
import java.util.UUID;

import com.lauter.serverce.enums.users.Gender;
import com.lauter.serverce.enums.users.Theme;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
@Schema(description = "Represents a user in the system")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
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

	@Schema(description = "The user's phone number")
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Schema(description = "The gender of the user")
	private Gender gender;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "gender")
	@Schema(description = "The theme preference of the user")
	private Theme theme;

	public User() {
	}

	public User(String name) {
		this.name = name;
	}
}
