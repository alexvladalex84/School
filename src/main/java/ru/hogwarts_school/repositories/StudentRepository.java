package ru.hogwarts_school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts_school.model.Student;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
