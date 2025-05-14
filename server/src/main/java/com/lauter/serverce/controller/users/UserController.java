package com.lauter.serverce.controller.users;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lauter.serverce.dto.ErrorResponse;
import com.lauter.serverce.dto.users.CreateUserDTO;
import com.lauter.serverce.dto.users.UserDTO;
import com.lauter.serverce.model.users.User;
import com.lauter.serverce.service.users.UserService;
import com.lauter.serverce.util.mappers.users.UserMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "Users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@GetMapping
	@Operation(summary = "Get all users", description = "Fetches a list of all users in the system", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the users", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))),
			@ApiResponse(responseCode = "204", description = "No users found"),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))) })
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		logger.debug("Received request to get all users");
		try {
			List<UserDTO> userDTOs = userService.getAllUsers().stream().map(UserMapper::toUserDTO)
					.collect(Collectors.toList());

			if (userDTOs.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(userDTOs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error occurred while retrieving all users", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get user by ID", description = "Fetches a user by their unique ID", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the user", content = {
					@Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "User not found", content = {
					@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = {
					@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json") }) })
	public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
		logger.debug("Received request to get user by ID: {}", id);

		try {
			return userService.getUserById(id).map(UserMapper::toUserDTO)
					.map(userDTO -> new ResponseEntity<>(userDTO, HttpStatus.OK))
					.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
		} catch (Exception e) {
			logger.error("Error occurred while retrieving user by ID", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	@Operation(summary = "Create a new user", description = "Creates a new user in the system", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User object that needs to be created"), responses = {
			@ApiResponse(responseCode = "201", description = "User created successfully", content = {
					@Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Invalid data provided", content = {
					@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = {
					@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json") }) })
	public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserDTO createUserDTO) {
		logger.debug("Received request to create user: {}", createUserDTO);

		try {
			UserDTO createdUser = userService.createUser(createUserDTO);
			return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Error occurred while creating user", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update user details", description = "Updates the details of an existing user", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully updated the user", content = {
					@Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Invalid data provided", content = {
					@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", description = "User not found", content = {
					@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = {
					@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json") }) })
	public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
		logger.debug("Received request to update user with ID {}: {}", id, userDTO);

		try {
			User user = UserMapper.fromUserDTO(userDTO);
			User updatedUser = userService.updateUser(id, user);
			return new ResponseEntity<>(UserMapper.toUserDTO(updatedUser), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error occurred while updating user with ID {}", id, e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a user", description = "Deletes a user by their unique ID", responses = {
			@ApiResponse(responseCode = "204", description = "User deleted successfully", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = {
					@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json") }) })
	public ResponseEntity<Void> deleteUser(
			@Parameter(description = "The unique ID of the user to delete", required = true) @PathVariable UUID id) {
		logger.debug("Received request to delete user with ID: {}", id);

		try {
			userService.deleteUser(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			logger.error("Error occurred while deleting user with ID {}", id, e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
