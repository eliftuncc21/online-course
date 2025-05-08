package org.example.onlinecourse.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.repository.UserProjection;
import org.example.onlinecourse.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/course-users")
public class UserController {
    private final UserService userService;

    @GetMapping("/list-users")
    public Page<UserProjection> listUsers(@RequestParam int page, @RequestParam int size) {
        return userService.listUsers(page, size);
    }

    @GetMapping("/list-users/{id}")
    public Page<UserProjection> findUserById(@PathVariable(name = "id") Long id,
                                             @RequestParam int page,
                                             @RequestParam int size) {
        return userService.findUserById(id, page, size);
    }
}
