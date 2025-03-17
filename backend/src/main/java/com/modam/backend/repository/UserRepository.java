package com.modam.backend.repository;

import com.modam.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)  // 읽기 전용으로 설정(성능 최적화)
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUserId(String userId);

    //Optional<User> findByUserName(String userName);

}
