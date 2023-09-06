package ru.hogwarts_school.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts_school.model.Faculty;
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


    @GetMapping
    public ResponseEntity getAllStudentByParameters(@RequestParam(required = false) Long Id,
                                                    @RequestParam(required = false) Integer age,
                                                    @RequestParam(required = false) String name

    ) {
        return studentService.getAllStudent(Id, age, name);
    }

    @GetMapping("/By-Age-Between")
    public List<Student> findByAgeBetween(@RequestParam int min, @RequestParam int max) {
        return studentService.findByAgeBetween(min, max);
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

    @GetMapping("/faculty-by-idOfStudent")
    public Faculty getFaculty(@RequestParam long id) {
        return studentService.getFaculty(id);
    }
}
/*1. Получить факультет студента
2. Получить студентов факультета*/
