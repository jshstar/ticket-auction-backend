package com.sparta.ticketauction.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sparta.ticketauction.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Boolean existsByEmailAndIsDeletedIsFalse(String email);

	Boolean existsByNicknameAndIsDeletedIsFalse(String nickname);

	Optional<User> findByEmailAndIsDeletedIsFalse(String email);

	Boolean existsByPhoneNumberAndIsDeletedIsFalse(String phoneNumber);

	// id를 사용하여 point 조회
	@Query("SELECT u.point FROM User u WHERE u.id = :userId")
	Long findPointById(@Param("userId") Long userId);
}
