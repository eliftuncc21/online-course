package org.example.onlinecourse.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.UserRequestDto;
import org.example.onlinecourse.dto.response.UserResponseDto;
import org.example.onlinecourse.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/save-user")
    public UserResponseDto addUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.addUser(userRequestDto);
    }

    @GetMapping("/list-users")
    public List<UserResponseDto> listUsers() {
        return userService.listUsers();
    }

    @GetMapping("/list-users/{id}")
    public UserResponseDto findUserById(@PathVariable(name = "id") Long id) {
        return userService.findUserById(id);
    }

    @DeleteMapping("/delete-users/{id}")
    public void deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/update-user/{id}")
    public UserResponseDto updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        return userService.updateUser(id, userRequestDto);
    }
}
