package com.lauter.serverce.controller.users;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lauter.serverce.dto.users.CreateUserDTO;
import com.lauter.serverce.dto.users.UserDTO;
import com.lauter.serverce.model.users.User;
import com.lauter.serverce.service.users.UserService;
import com.lauter.serverce.util.mappers.users.UserMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the users"),
			@ApiResponse(responseCode = "404", description = "No users found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public List<UserDTO> getAllUsers() {
		logger.debug("Received request to get all users");
		return userService.getAllUsers().stream().map(UserMapper::toUserDTO).toList();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get user by ID", description = "Fetches a user by their unique ID", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the user"),
			@ApiResponse(responseCode = "404", description = "User not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
		logger.debug("Received request to get user by ID: {}", id);
		return userService.getUserById(id).map(UserMapper::toUserDTO).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@Operation(summary = "Create a new user", description = "Creates a new user in the system", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User object that needs to be created"), responses = {
			@ApiResponse(responseCode = "201", description = "User created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid user data"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public UserDTO createUser(@RequestBody CreateUserDTO user) {
		logger.debug("Received request to create user: {}", user);
		return userService.createUser(user);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update user details", description = "Updates the details of an existing user", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully updated the user"),
			@ApiResponse(responseCode = "404", description = "User not found"),
			@ApiResponse(responseCode = "400", description = "Invalid user data"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
		logger.debug("Received request to update user with ID {}: {}", id, userDTO);
		User user = UserMapper.fromUserDTO(userDTO);
		User updatedUser = userService.updateUser(id, user);
		return ResponseEntity.ok(UserMapper.toUserDTO(updatedUser));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a user", description = "Deletes a user by their unique ID", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully deleted the user"),
			@ApiResponse(responseCode = "404", description = "User not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public void deleteUser(
			@Parameter(description = "The unique ID of the user to delete", required = true) @PathVariable UUID id) {
		logger.debug("Received request to delete user with ID: {}", id);
		userService.deleteUser(id);
	}
}
