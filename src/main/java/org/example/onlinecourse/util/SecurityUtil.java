package org.example.onlinecourse.util;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.exception.ErrorMessage;
import org.example.onlinecourse.exception.MessageType;
import org.example.onlinecourse.model.User;
import org.example.onlinecourse.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtil {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ErrorMessage(MessageType.USER_NOT_FOUND,"User Not Found", HttpStatus.NOT_FOUND));
    }

}
