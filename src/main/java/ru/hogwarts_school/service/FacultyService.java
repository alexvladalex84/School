package ru.hogwarts_school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts_school.exceptions.FacultyNotFoundException;
import ru.hogwarts_school.model.Faculty;
import ru.hogwarts_school.model.Student;
import ru.hogwarts_school.repositories.FacultyRepository;

import java.util.List;

@Service
public class FacultyService {
    Logger LOG = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;
    private final StudentService studentService;

    public FacultyService(FacultyRepository facultyRepository, StudentService studentService) {
        this.facultyRepository = facultyRepository;
        this.studentService = studentService;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);

    }

    public Faculty editFaculty(Long id, Faculty faculty) {
        Faculty fa = facultyRepository.getById(id);
        fa.setName(faculty.getName());
        fa.setColor(faculty.getColor());

        return facultyRepository.save(fa);
    }


    public void delete(long Id) {
        facultyRepository.deleteById(Id);
    }


    public ResponseEntity getAllFaculty(Long Id, String partName, String partColor) {
        if (Id != null) {
            return ResponseEntity.ok(facultyRepository.findById(Id).get());
        }
        if (partColor != null) {
            return ResponseEntity.ok(facultyRepository.findByColorContainsIgnoreCase(partColor));
        }
        if (partName != null) {
            return ResponseEntity.ok(facultyRepository.findByNameContainsIgnoreCase(partName));
        }

        return ResponseEntity.ok(facultyRepository.findAll());
    }

    public Faculty getFacultyById(Long id) {
LOG.info("был вызван метод : getFacultyById(Long id)");
LOG.error("ошибка : getFacultyById(Long id) введён не корректный id");
        return facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
    }

    public List<Faculty> findByNameContainsIgnoreCaseOrColorContainsIgnoreCase(String param) {
        return facultyRepository.findByNameContainsIgnoreCaseOrColorContainsIgnoreCase(param, param);
    }

    public List<Student> getStudentByIdOfFaculty(Long facultyID) {
        return studentService.findByFacultyId(facultyID);
    }

}
