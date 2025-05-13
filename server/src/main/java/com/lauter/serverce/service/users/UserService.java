package com.lauter.serverce.service.users;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lauter.serverce.dto.users.CreateUserDTO;
import com.lauter.serverce.dto.users.UserDTO;
import com.lauter.serverce.enums.users.Theme;
import com.lauter.serverce.model.users.User;
import com.lauter.serverce.repository.users.UserRepository;
import com.lauter.serverce.util.mappers.users.UserMapper;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUsers() {
		logger.info("Fetching all users...");
		List<User> users = userRepository.findAll();
		if (users.isEmpty()) {
			logger.warn("No users found.");
		} else {
			logger.info("Successfully fetched {} users.", users.size());
		}
		return users;
	}

	public Optional<User> getUserById(UUID id) {
		logger.info("Fetching user by ID: {}", id);
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			logger.info("Successfully fetched user with ID: {}", id);
		} else {
			logger.warn("User with ID: {} not found.", id);
		}
		return user;
	}

	public UserDTO createUser(CreateUserDTO userDTO) {
		logger.info("Creating a new user with name: {}", userDTO.getName());

		User user = UserMapper.fromCreateUserDTO(userDTO);

		int age = calculateAge(user.getDateOfBirth());
		user.setAge(age);
		user.setTheme(Theme.LIGHT_MODE);

		User savedUser = userRepository.save(user);

		return UserMapper.toUserDTO(savedUser);
	}

	public User updateUser(UUID id, User user) {
		logger.info("Updating user with ID: {}", id);
		if (userRepository.existsById(id)) {
			user.setId(id);
			User updatedUser = userRepository.save(user);
			logger.info("User with ID: {} successfully updated.", id);
			return updatedUser;
		} else {
			logger.error("User with ID: {} not found for update.", id);
			throw new RuntimeException("User not found");
		}
	}

	public void deleteUser(UUID id) {
		logger.info("Deleting user with ID: {}", id);
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
			logger.info("User with ID: {} successfully deleted.", id);
		} else {
			logger.error("User with ID: {} not found for deletion.", id);
			throw new RuntimeException("User not found");
		}
	}

	private int calculateAge(LocalDate dateOfBirth) {
		return Period.between(dateOfBirth, LocalDate.now()).getYears();
	}
}
