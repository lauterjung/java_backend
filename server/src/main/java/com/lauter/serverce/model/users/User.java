package com.lauter.serverce.model.users;

import java.sql.Date;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	private Date dateOfBirth;

	@Schema(description = "The age of the user")
	private int age;

	public User() {
	}

	public User(String name) {
		this.name = name;
	}
}
