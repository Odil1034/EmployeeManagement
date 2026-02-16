package com.example.EmployeeManagement.service.token;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.example.EmployeeManagement.dto.Response;
import com.example.EmployeeManagement.dto.auth.TokenResponseDTO;
import com.example.EmployeeManagement.exception.TokenExpiredException;
import com.example.EmployeeManagement.exception.UserNotFoundException;
import com.example.EmployeeManagement.repository.UserRepository;
import com.example.EmployeeManagement.utils.JwtTokenUtils;

/**
 * @author Baxriddinov Odiljon
 * @since 11/February/2025  20:05
 **/
@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public Response<TokenResponseDTO> generateToken(@NotNull String username, @NotNull String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User found with username: {0}", username)
        );
        var accessToken = jwtTokenUtils.generateAccessToken(user.getId(), user.getUsername(), user.getRoles());
        var refreshToken = jwtTokenUtils.generateRefreshToken(user.getId(), user.getUsername());
        return Response.ok(TokenResponseDTO.of(user.getId(), accessToken, refreshToken));
    }

    public Response<TokenResponseDTO> refreshToken(@NotNull String refreshToken) {
        if (!jwtTokenUtils.validateToken(refreshToken))
            throw new TokenExpiredException("Token has expired or invalid: {0}", refreshToken);
        var username = jwtTokenUtils.extractUsername(refreshToken);
        var user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User not found: {0}", username)
        );
        if (user == null)
            throw new UserNotFoundException("User not found: {0}", username);
        var newAccessToken = jwtTokenUtils.generateAccessToken(user.getId(), user.getUsername(), user.getRoles());
        var newRefreshToken = jwtTokenUtils.generateRefreshToken(user.getId(), user.getUsername());
        return Response.ok(TokenResponseDTO.of(user.getId(), newAccessToken, newRefreshToken));
    }
}

