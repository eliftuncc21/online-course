package org.example.onlinecourse.config;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    public final String AUTHENTICATE = "/authenticate";
    public final String REGISTER = "/rest/api/course-users/save-users";
    public final String REFRESH_TOKEN = "/refreshToken";

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(request ->
                 request.requestMatchers(AUTHENTICATE, REGISTER, REFRESH_TOKEN)
                .permitAll()
                .requestMatchers(
                        "/rest/api/course/**",
                        "/rest/api/comment/**",
                        "/rest/api/enrollment/**",
                        "/rest/api/category/**",
                        "/rest/api/instructor/**",
                        "/rest/api/sub-category/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(
                        "/rest/api/course/**",
                        "/rest/api/comment/**",
                        "/rest/api/enrollment/**").hasAuthority("ROLE_INSTRUCTOR")
                .anyRequest().authenticated())
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}