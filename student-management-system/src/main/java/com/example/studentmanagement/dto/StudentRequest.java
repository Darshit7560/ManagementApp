package com.example.studentmanagement.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record StudentRequest(
    @NotBlank @Size(max = 80) String firstName,
    @NotBlank @Size(max = 80) String lastName,
    @NotBlank @Email @Size(max = 150) String email,
    @NotBlank @Size(max = 100) String course,
    @Past LocalDate dateOfBirth
) {}
