package com.example.studentmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "students", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 80)
    private String firstName;
    @Column(nullable = false, length = 80)
    private String lastName;
    @Column(nullable = false, unique = true, length = 150)
    private String email;
    @Column(nullable = false, length = 100)
    private String course;
    private LocalDate dateOfBirth;

    protected Student() {}
    public Student(String firstName, String lastName, String email, String course, LocalDate dateOfBirth) {
        this.firstName = firstName; this.lastName = lastName; this.email = email;
        this.course = course; this.dateOfBirth = dateOfBirth;
    }
    public void update(String firstName, String lastName, String email, String course, LocalDate dateOfBirth) {
        this.firstName = firstName; this.lastName = lastName; this.email = email;
        this.course = course; this.dateOfBirth = dateOfBirth;
    }
    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getCourse() { return course; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
}
