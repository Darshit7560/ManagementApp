package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.*;
import com.example.studentmanagement.entity.*;
import com.example.studentmanagement.repository.AppUserRepository;
import com.example.studentmanagement.security.JwtService;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final AppUserRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AppUserRepository users, PasswordEncoder encoder,
                       AuthenticationManager authenticationManager, JwtService jwtService) {
        this.users = users; this.encoder = encoder;
        this.authenticationManager = authenticationManager; this.jwtService = jwtService;
    }
    @Transactional
    public void register(RegisterRequest request) {
        if (users.existsByUsername(request.username()))
            throw new IllegalArgumentException("Username is already taken");
        users.save(new AppUser(request.username(), encoder.encode(request.password()), Role.USER));
    }
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        return new AuthResponse(jwtService.generateToken(request.username()), "Bearer");
    }
}
