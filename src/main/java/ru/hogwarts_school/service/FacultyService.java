package ru.hogwarts_school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts_school.model.Faculty;
import ru.hogwarts_school.model.Student;
import ru.hogwarts_school.repositories.FacultyRepository;

import java.util.List;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentService studentService;

    public FacultyService(FacultyRepository facultyRepository, StudentService studentService) {
        this.facultyRepository = facultyRepository;
        this.studentService = studentService;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);

    }

//    public Faculty findFaculty(long Id) {
//       return facultyRepository.findById(Id).get();
//    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
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

    public List<Faculty> findByNameContainsIgnoreCaseOrColorContainsIgnoreCase(String param) {
        return facultyRepository.findByNameContainsIgnoreCaseOrColorContainsIgnoreCase(param, param);
    }

    public List<Student> getStudentByIdOfFaculty(Long facultyID) {
        return studentService.findByFacultyId(facultyID);
    }

}
