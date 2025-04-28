package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.RefreshTokenRequestDto;
import org.example.onlinecourse.dto.response.LoginResponseDto;
import org.example.onlinecourse.jwt.JwtService;
import org.example.onlinecourse.model.RefreshToken;
import org.example.onlinecourse.model.User;
import org.example.onlinecourse.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final RefreshTokenManager refreshTokenManager;

    public RefreshToken createRefreshToken(User user) {
        return refreshTokenManager.createRefreshToken(user);
    }

    public boolean isTokenExpired(Date expiredDate){
        return expiredDate.before(new Date());
    }

    public LoginResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequest) {
        Optional<RefreshToken> optional = refreshTokenRepository.findByRefreshToken(refreshTokenRequest.getRefreshToken());

        if(optional.isEmpty()){
            System.out.println("Refresh token not found: " + refreshTokenRequest.getRefreshToken());
        }

        RefreshToken refreshToken = optional.get();
        if(isTokenExpired(refreshToken.getExpiredDate())){
            System.out.println("Refresh token expired: " + refreshToken.getExpiredDate());
        }

        String accessToken = jwtService.generateToken(refreshToken.getUser());
        RefreshToken newRefreshToken = refreshTokenRepository.save(createRefreshToken(refreshToken.getUser()));

        return new LoginResponseDto(accessToken, newRefreshToken.getRefreshToken());
    }
}
