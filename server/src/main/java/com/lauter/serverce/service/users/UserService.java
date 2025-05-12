package com.lauter.serverce.service.users;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lauter.serverce.model.users.User;
import com.lauter.serverce.repository.users.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(UUID id) {
		return userRepository.findById(id);
	}

	public User createUser(User user) {
		return userRepository.save(user);
	}

	public User updateUser(UUID id, User user) {
		if (userRepository.existsById(id)) {
            user.setId(id);
			return userRepository.save(user);
		} else {
			throw new RuntimeException("User not found");
		}
	}

	public void deleteUser(UUID id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
		} else {
			throw new RuntimeException("User not found");
		}
	}
}
