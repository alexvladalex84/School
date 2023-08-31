package ru.hogwarts_school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts_school.exceptions.FacultyDoesNotExistException;
import ru.hogwarts_school.model.Faculty;
import ru.hogwarts_school.repositories.FacultyRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyMap) {
        this.facultyRepository = facultyMap;
    }


    public Faculty addFaculty(Faculty faculty) {
       return facultyRepository.save(faculty);

    }

    public Faculty findFaculty(long Id) {
       return facultyRepository.findById(Id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void delete(long Id) {
       facultyRepository.deleteById(Id);
    }

//        public List<Faculty> getAllByColor(String color) {
////        return facultyRepository.values().stream()
////                .filter(student -> student.getColor().equals(color))
////                .collect(Collectors.toList());
//         return    facultyRepository.findAll();
//    }
    public List<Faculty> getAllFaculty() {
      return   facultyRepository.findAll();
    }

}
