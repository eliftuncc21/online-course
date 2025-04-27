package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.UserRequestDto;
import org.example.onlinecourse.dto.response.UserResponseDto;
import org.example.onlinecourse.mapper.UserMapper;
import org.example.onlinecourse.model.User;
import org.example.onlinecourse.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto addUser(UserRequestDto userRequestDto) {
        User user = userMapper.toUser(userRequestDto);
        User dbUser = userRepository.save(user);
        return userMapper.toUserResponseDto(dbUser);
    }

    public List<UserResponseDto> listUsers() {
        List<User> userList = userRepository.findAll();
        return userMapper.toUserResponseDtoList(userList);
    }

    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return userMapper.toUserResponseDto(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        userRepository.delete(user);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id).orElse(null);
        userMapper.updateUser(userRequestDto, user);

        User dbUser = userRepository.save(user);
        return userMapper.toUserResponseDto(dbUser);
    }
}