package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.repository.UserProjection;
import org.example.onlinecourse.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Page<UserProjection> listUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAllBy(pageable);
    }

    public Page<UserProjection> findUserById(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAllByUserId(id, pageable);
    }
}