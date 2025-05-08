package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.RefreshTokenRequestDto;
import org.example.onlinecourse.dto.response.LoginResponseDto;
import org.example.onlinecourse.exception.ErrorMessage;
import org.example.onlinecourse.exception.MessageType;
import org.example.onlinecourse.jwt.JwtService;
import org.example.onlinecourse.model.RefreshToken;
import org.example.onlinecourse.model.User;
import org.example.onlinecourse.repository.RefreshTokenRepository;
import org.springframework.http.HttpStatus;
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
            throw new ErrorMessage(
                    MessageType.REFRESH_TOKEN_NOT_FOUND,
                    "Not found refresh token",
                    HttpStatus.NOT_FOUND
            );
        }

        RefreshToken refreshToken = optional.get();
        if(isTokenExpired(refreshToken.getExpiredDate())){
            throw new ErrorMessage(
                    MessageType.REFRESH_TOKEN_EXPIRED,
                    "Refresh Token Hatası",
                    HttpStatus.UNAUTHORIZED);
        }

        String accessToken = jwtService.generateToken(refreshToken.getUser());
        RefreshToken newRefreshToken = refreshTokenRepository.save(createRefreshToken(refreshToken.getUser()));

        return new LoginResponseDto(accessToken, newRefreshToken.getRefreshToken());
    }
}
