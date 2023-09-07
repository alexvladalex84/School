package ru.hogwarts_school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts_school.model.Faculty;
import ru.hogwarts_school.model.Student;
import ru.hogwarts_school.repositories.StudentRepository;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {

        return studentRepository.save(student);
    }


    public Student editStudent(Student student) {

        return studentRepository.save(student);
    }

    public void delete(long id) {
//        Student student = studentMap.get(id);
//        if (studentMap.containsKey(id)) {
//            studentMap.remove(id);
//            return student;
//        }
//        throw new StudentDoesNotExistException("Студента с таким именем не существует");
        studentRepository.deleteById(id);

    }


    public ResponseEntity getAllStudent(Long Id, Integer age, String name) {
        if (Id != null) {
            return ResponseEntity.ok(studentRepository.findById(Id));
        }
        if (age != null) {
            return ResponseEntity.ok(studentRepository.findByAge(age));
        }
        if (name != null) {
            return ResponseEntity.ok(studentRepository.findStudentByNameContainsIgnoreCase(name));
        }

        return ResponseEntity.ok(studentRepository.findAll());
    }

    public Student findById(Long id) {
        return studentRepository.findById(id).get();
    }

    public List<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFaculty(Long studentId) {
        return studentRepository.findById(studentId).get().getFaculty();
    }

    public List<Student> findByFacultyId(long facultyId) {
        return studentRepository.findByFacultyId(facultyId);
    }
}
