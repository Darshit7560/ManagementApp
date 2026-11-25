package com.example.studentmanagement.config;

import com.example.studentmanagement.entity.*;
import com.example.studentmanagement.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminUserInitializer {
    @Bean
    CommandLineRunner createInitialAdmin(AppUserRepository users, PasswordEncoder encoder) {
        return args -> {
            if (!users.existsByUsername("admin")) {
                users.save(new AppUser("admin", encoder.encode("Admin@12345"), Role.ADMIN));
            }
        };
    }
}
