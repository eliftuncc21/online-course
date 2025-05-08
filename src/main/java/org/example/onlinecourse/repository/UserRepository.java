package org.example.onlinecourse.repository;

import org.example.onlinecourse.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Page<UserProjection> findAllBy(Pageable pageable);
    Page<UserProjection> findAllByUserId(Long userId, Pageable pageable);
}
