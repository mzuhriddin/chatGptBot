package com.example.chatgptbot.repository;

import com.example.chatgptbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByChatId(String chatId);
    Optional<User> findByPhone(String phone);
}
