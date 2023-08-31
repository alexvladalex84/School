package ru.hogwarts_school.service;

import org.springframework.stereotype.Service;
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

    public Student findStudent(long Id) {
        return studentRepository.findById(Id).get();
    }

    public Student editStudent(Student student) {
//
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

//    public List<Student> getAllByAge(int age) {
//        return studentMap.values().stream()
//                .filter(student -> student.getAge() == age)
//                .collect(Collectors.toList());
//    }

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }


}
