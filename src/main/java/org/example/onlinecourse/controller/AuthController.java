package org.example.onlinecourse.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.LoginRequestDto;
import org.example.onlinecourse.dto.request.RefreshTokenRequestDto;
import org.example.onlinecourse.dto.response.LoginResponseDto;
import org.example.onlinecourse.service.AuthService;
import org.example.onlinecourse.service.RefreshTokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/authenticate")
    public LoginResponseDto authenticate(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.authenticate(loginRequestDto);
    }

    @PostMapping("/refreshToken")
    public LoginResponseDto refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequest) {
        return refreshTokenService.refreshToken(refreshTokenRequest);
    }
}
