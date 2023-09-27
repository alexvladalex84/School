package ru.hogwarts_school.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts_school.model.Faculty;
import ru.hogwarts_school.model.Student;
import ru.hogwarts_school.service.AvatarService;
import ru.hogwarts_school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
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

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.findById(id);
    }

//    @GetMapping
//    public ResponseEntity getAllStudent() {
//        return studentService.getAll();
//    }

    @GetMapping("/By-Age-Between")
    public List<Student> findByAgeBetween(@RequestParam int min, @RequestParam int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @PutMapping("/{id}")
    public Student editStudent(@PathVariable long id, @RequestBody Student student) {
        return studentService.editStudent(id, student);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam Long id) {
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/faculty-by-idOfStudent")
    public Faculty getFacultyByIdStudent(@RequestParam long id) {
        return studentService.getFaculty(id);
    }


    @GetMapping("/count_students")
    public Integer getCountStudent() {
        return studentService.getCountStudent();
    }

    @GetMapping("/Average_Age_Students")
    public Integer getAverageAgeStudent() {

        return studentService.getCountAverageAge();
    }

    @GetMapping("/get_last_students")
    public List<Student> lastStudents() {
        return studentService.lastStudents();
    }
}


