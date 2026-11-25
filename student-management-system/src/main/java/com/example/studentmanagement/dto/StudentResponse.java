package com.example.studentmanagement.dto;

import com.example.studentmanagement.entity.Student;
import java.time.LocalDate;

public record StudentResponse(Long id, String firstName, String lastName, String email,
                              String course, LocalDate dateOfBirth) {
    public static StudentResponse from(Student s) {
        return new StudentResponse(s.getId(), s.getFirstName(), s.getLastName(),
                s.getEmail(), s.getCourse(), s.getDateOfBirth());
    }
}
