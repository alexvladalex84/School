package ru.hogwarts_school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts_school.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(Integer age);

    List<Student> findStudentByNameContainsIgnoreCase(String partName);

    List<Student> findByAgeBetween(int min, int max);

    List<Student> findByFacultyId(Long id);



}
