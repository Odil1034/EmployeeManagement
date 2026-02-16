package com.example.EmployeeManagement.utils;

import com.example.EmployeeManagement.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.example.EmployeeManagement.dto.auth.TokenDTO;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Odiljon Baxriddinov
 * @since 2/February/2026  22:20
 **/
@Component
public class JwtTokenUtils {

    @Value("${token.secret-key}")
    private String SECRET_KEY;

    @Value("${token.expiration.access}")
    private long ACCESS_TOKEN_EXPIRATION;

    @Value("${token.expiration.refresh}")
    private long REFRESH_TOKEN_EXPIRATION;

    public TokenDTO generateAccessToken(@NonNull Long id, String username, Set<Role> roles) {
        Set<String> roleNames = roles.stream()
                .map(Role::getName)
                .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                .collect(Collectors.toSet());

        Map<String, Object> claims = Map.of(
                "roles", roleNames,
                "id", id
        );
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime expiredAt = issuedAt.plusSeconds(ACCESS_TOKEN_EXPIRATION / 1000);
        String token = Jwts.builder()
                .subject(username)
                .claims(claims)
                .issuedAt(Date.from(issuedAt.atZone(ZoneId.systemDefault()).toInstant()))
                .expiration(Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(getSignKey())
                .compact();
        return new TokenDTO(token, issuedAt, expiredAt, ACCESS_TOKEN_EXPIRATION / 1000);
    }

    public TokenDTO generateRefreshToken(@NonNull Long id, @NonNull String username) {
        Map<String, Object> claims = Map.of(
                "id", id
        );
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime expiredAt = issuedAt.plusSeconds(REFRESH_TOKEN_EXPIRATION / 1000);
        String token = Jwts.builder()
                .subject(username)
                .claims(claims)
                .issuedAt(Date.from(issuedAt.atZone(ZoneId.systemDefault()).toInstant()))
                .expiration(Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(getSignKey())
                .compact();
        return new TokenDTO(token, issuedAt, expiredAt, REFRESH_TOKEN_EXPIRATION / 1000);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractId(String token) {
        return extractAllClaims(token).get("id", Long.class);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }


    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isTokenExpired(String token) {
        try {
            final Date expiration = extractClaim(token, Claims::getExpiration);
            return expiration != null && expiration.before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return true;
        }
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }
}
