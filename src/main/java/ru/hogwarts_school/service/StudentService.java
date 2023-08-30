package ru.hogwarts_school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts_school.exceptions.StudentDoesNotExistException;
import ru.hogwarts_school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final Map<Long, Student> studentMap = new HashMap<>();
    private static long lastId = 0;


    public Student addStudent(String name, int age) {

        Student student = new Student(++lastId, name, age);
        studentMap.put(student.getId(), student);

        return student;
    }

    public Student findStudent(long Id) {
        if (studentMap.containsKey(Id)) {
            return studentMap.get(Id);
        }
        throw new StudentDoesNotExistException("Студента с таким именем не существует");
    }

    public Student editStudent(long id, String name, int age) {
        Student student = studentMap.get(id);
        student.setName(name);
        student.setAge(age);
        return student;

    }

    public Student delete(long id) {
        Student student = studentMap.get(id);
        if (studentMap.containsKey(id)) {
            studentMap.remove(id);
            return student;
        }
        throw new StudentDoesNotExistException("Студента с таким именем не существует");
    }

    public List<Student> getAllByAge(int age) {
        return studentMap.values().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }

    public Map<Long, Student> getStudentMap() {
        return studentMap;
    }


}
