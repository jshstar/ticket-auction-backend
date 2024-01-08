package com.sparta.ticketauction.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.ticketauction.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Boolean existsByEmailAndIsDeletedIsFalse(String email);

	Boolean existsByNicknameAndIsDeletedIsFalse(String nickname);

	Optional<User> findByNickname(String nickname);
}
