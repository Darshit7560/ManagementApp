package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.*;
import com.example.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService service;
    public StudentController(StudentService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }
    @GetMapping
    public Page<StudentResponse> getAll(@RequestParam(defaultValue="0") int page,
                                        @RequestParam(defaultValue="10") int size,
                                        @RequestParam(defaultValue="id") String sortBy) {
        return service.getAll(page, size, sortBy);
    }
    @GetMapping("/{id}") public StudentResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }
    @PutMapping("/{id}") public StudentResponse update(@PathVariable Long id,
            @Valid @RequestBody StudentRequest request) { return service.update(id, request); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id); return ResponseEntity.noContent().build();
    }
}
