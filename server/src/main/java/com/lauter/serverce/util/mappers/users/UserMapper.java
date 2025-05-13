package com.lauter.serverce.util.mappers.users;

import com.lauter.serverce.dto.users.CreateUserDTO;
import com.lauter.serverce.dto.users.UserDTO;
import com.lauter.serverce.model.users.User;

public class UserMapper {
	public static UserDTO toUserDTO(User user) {
		return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getDateOfBirth(), user.getAge());
	}

	public static User fromUserDTO(UserDTO dto) {
		User user = new User();
		user.setId(dto.getId());
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		return user;
	}

	public static User fromCreateUserDTO(CreateUserDTO dto) {
		User user = new User();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPhoneNumber(dto.getPhoneNumber());
		user.setGender(dto.getGender());
		user.setDateOfBirth(dto.getDateOfBirth());
		return user;
	}

}
