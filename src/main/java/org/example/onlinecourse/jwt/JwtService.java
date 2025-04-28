package org.example.onlinecourse.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.onlinecourse.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtService {
    public static final String SECRET_KEY = "06WkSMDHSb9/MnQ0JR1GbHX6sugecQY9Bat6dFutUmQ=";

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claimsMap = new HashMap<>();
        String role = ((User) userDetails).getRole().name();
        claimsMap.put("role", role);
        return Jwts.builder()
                .setSubject(((User) userDetails).getEmail())
                .addClaims(claimsMap)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))
                .signWith(getKey(), SignatureAlgorithm.HS256).compact();
    }

    public Claims getClaims(String token){
         return  Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T exportToken(String token, Function<Claims, T> claimsTFunction){
        Claims claims = getClaims(token);
        return claimsTFunction.apply(claims);
    }

    public Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getEmail(String token){
        return exportToken(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token){
        Date expiredDate = exportToken(token, Claims::getExpiration);
        return expiredDate.before(new Date());
    }
}
