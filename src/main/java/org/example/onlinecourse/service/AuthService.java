package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.LoginRequestDto;
import org.example.onlinecourse.dto.response.LoginResponseDto;
import org.example.onlinecourse.jwt.JwtService;
import org.example.onlinecourse.model.RefreshToken;
import org.example.onlinecourse.model.User;
import org.example.onlinecourse.repository.RefreshTokenRepository;
import org.example.onlinecourse.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationProvider authenticationProvider;
    private final JwtService jwtService;
    private final RefreshTokenManager refreshTokenManager;

    public LoginResponseDto authenticate(LoginRequestDto loginRequestDto) {
        try{
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            authenticationProvider.authenticate(auth);
            Optional<User> optional = userRepository.findByEmail(loginRequestDto.getEmail());
            String accessToken = jwtService.generateToken(optional.get());
            RefreshToken refreshToken = createRefreshToken(optional.get());
            refreshTokenRepository.save(refreshToken);
            return new LoginResponseDto(accessToken, refreshToken.getRefreshToken());
        } catch (Exception e) {
            System.out.println("Kullanıcı adı veya şifre hatalı");
        }
        return null;
    }

    public RefreshToken createRefreshToken(User user) {
        return refreshTokenManager.createRefreshToken(user);
    }

}
