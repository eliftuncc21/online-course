package org.example.onlinecourse.config;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    public final String AUTHENTICATE = "/authenticate";
    public final String[] REGISTER = {
            "/rest/api/admin/save-admin",
            "/rest/api/student/save-student",
            "/rest/api/instructor/save-instructor"
    };
    public final String REFRESH_TOKEN = "/refreshToken";
    public final String[] SWAGGER_PATHS = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html"
    };

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(request ->
                 request.requestMatchers(AUTHENTICATE, REFRESH_TOKEN)
                .permitAll()
                .requestMatchers(REGISTER).permitAll()
                .requestMatchers(SWAGGER_PATHS).permitAll()
                .anyRequest().authenticated())
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}