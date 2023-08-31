package ru.hogwarts_school.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts_school.model.Student;
import ru.hogwarts_school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudentInfo(@PathVariable long id) {
        return studentService.findStudent(id);
    }

//    @GetMapping
//    public List<Student> getAllByAge(@RequestParam int age) {
//        return studentService.getAllByAge(age);
//    }

    @GetMapping
    public List<Student> getAllStudent() {
        return studentService.getAllStudent();
    }

    @PutMapping
    public Student editStudent(@RequestBody Student student) {
        return studentService.editStudent(student);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam long id) {
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }


}
