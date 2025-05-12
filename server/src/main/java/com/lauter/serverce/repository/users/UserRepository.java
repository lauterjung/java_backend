package com.lauter.serverce.repository.users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lauter.serverce.model.users.User;

public interface UserRepository extends JpaRepository<User, UUID> {
}