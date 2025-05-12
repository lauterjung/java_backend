package com.lauter.serverce.controller.users;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lauter.serverce.model.users.User;
import com.lauter.serverce.service.users.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "Users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	@Operation(summary = "Get all users", description = "Fetches a list of all users in the system", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the users"),
			@ApiResponse(responseCode = "404", description = "No users found") })
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get user by ID", description = "Fetches a user by their unique ID", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved the user"),
			@ApiResponse(responseCode = "404", description = "User not found") })

	public Optional<User> getUserById(
			@Parameter(description = "The unique ID of the user", required = true) @PathVariable UUID id) {
		return userService.getUserById(id);
	}

	@PostMapping
	@Operation(summary = "Create a new user", description = "Creates a new user in the system", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User object that needs to be created"), responses = {
			@ApiResponse(responseCode = "201", description = "User created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid user data") })
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update user details", description = "Updates the details of an existing user", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully updated the user"),
			@ApiResponse(responseCode = "404", description = "User not found"),
			@ApiResponse(responseCode = "400", description = "Invalid user data") })
	public User updateUser(
			@Parameter(description = "The unique ID of the user to update", required = true) @PathVariable UUID id,
			@RequestBody User user) {
		return userService.updateUser(id, user);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a user", description = "Deletes a user by their unique ID", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully deleted the user"),
			@ApiResponse(responseCode = "404", description = "User not found") })
	public void deleteUser(
			@Parameter(description = "The unique ID of the user to delete", required = true) @PathVariable UUID id) {
		userService.deleteUser(id);
	}
}