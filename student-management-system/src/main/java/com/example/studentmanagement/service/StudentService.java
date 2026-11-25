package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.*;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.StudentRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentService {
    private final StudentRepository repository;
    public StudentService(StudentRepository repository) { this.repository = repository; }

    public StudentResponse create(StudentRequest r) {
        if (repository.existsByEmail(r.email())) throw new IllegalArgumentException("Email already exists");
        Student s = new Student(r.firstName(), r.lastName(), r.email(), r.course(), r.dateOfBirth());
        return StudentResponse.from(repository.save(s));
    }
    @Transactional(readOnly = true)
    public Page<StudentResponse> getAll(int page, int size, String sortBy) {
        if (page < 0 || size < 1 || size > 100) throw new IllegalArgumentException("Invalid page or size");
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return repository.findAll(pageable).map(StudentResponse::from);
    }
    @Transactional(readOnly = true)
    public StudentResponse getById(Long id) { return StudentResponse.from(findStudent(id)); }

    public StudentResponse update(Long id, StudentRequest r) {
        Student s = findStudent(id);
        if (repository.existsByEmailAndIdNot(r.email(), id))
            throw new IllegalArgumentException("Email already belongs to another student");
        s.update(r.firstName(), r.lastName(), r.email(), r.course(), r.dateOfBirth());
        return StudentResponse.from(repository.save(s));
    }
    public void delete(Long id) { repository.delete(findStudent(id)); }

    private Student findStudent(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
    }
}
