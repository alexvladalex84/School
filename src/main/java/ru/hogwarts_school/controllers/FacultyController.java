package ru.hogwarts_school.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts_school.model.Faculty;
import ru.hogwarts_school.model.Student;
import ru.hogwarts_school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

//    @GetMapping("/{id}")
//    public Faculty getFacultyInfo(@PathVariable long id) {
//        return facultyService.findFaculty(id);
//    }

    @PutMapping
    public Faculty editFaculty(@RequestBody Faculty faculty) {
        return facultyService.editFaculty(faculty);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam long id) {
        facultyService.delete(id);
        return ResponseEntity.ok().build();
    }

    //    @GetMapping
//    public List<Faculty> getAllByColor(@RequestParam String color) {
//        return facultyService.getAllByColor(color);
//    }
    @GetMapping
    public ResponseEntity getAllFaculty(@RequestParam(required = false) Long Id,
                                        @RequestParam(required = false) String partName,
                                        @RequestParam(required = false) String partColor) {

        return facultyService.getAllFaculty(Id, partName, partColor);
    }

    @GetMapping("/name-or-color")
    public List<Faculty> findByNameContainsIgnoreCaseOrColorContainsIgnoreCase(@RequestParam String param) {
        return facultyService.findByNameContainsIgnoreCaseOrColorContainsIgnoreCase(param);
    }

    @GetMapping("/student-byIdFaculty")
    public List<Student> studentByIdFaculty(@RequestParam Long IdFaculty) {
        return facultyService.getStudentByIdOfFaculty(IdFaculty);
    }
}
