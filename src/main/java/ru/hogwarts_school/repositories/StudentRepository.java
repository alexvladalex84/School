package ru.hogwarts_school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts_school.model.Student;

import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(Integer age);

    List<Student> findStudentByNameContainsIgnoreCase(String partName);

    List<Student> findByAgeBetween(int min, int max);

    List<Student> findByFacultyId(Long id);

    @Query(value = "select   COUNT(id) FROM student s ", nativeQuery = true)
    Integer getCountStudent();

    @Query(value = "select   avg(age) from student ", nativeQuery = true)
    Double getCountAverageAge();

    @Query(value =" SELECT * FROM student s  ORDER BY id DESC LIMIT 5",nativeQuery = true)
    List<Student> lastStudents();
}
