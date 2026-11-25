package com.example.studentmanagement.service;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String message) { super(message); }
}
