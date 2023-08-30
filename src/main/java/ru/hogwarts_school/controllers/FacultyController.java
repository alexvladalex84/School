package ru.hogwarts_school.controllers;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts_school.model.Faculty;
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
        return facultyService.addFaculty(faculty.getName(), faculty.getColor());
    }

    @GetMapping("/{id}")
    public Faculty getFacultyInfo(@PathVariable long id) {
        return facultyService.findFaculty(id);
    }

    @PutMapping
    public Faculty editFaculty(@RequestBody Faculty faculty) {
        return facultyService.editFaculty(faculty.getId(), faculty.getName(), faculty.getColor());
    }

    @DeleteMapping
    public Faculty delete(@RequestParam long id) {
        return facultyService.delete(id);

    }

    @GetMapping
    public List<Faculty> getAllByColor(@RequestParam String color) {
        return facultyService.getAllByColor(color);
    }
}
