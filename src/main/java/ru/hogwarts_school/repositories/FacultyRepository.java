package ru.hogwarts_school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts_school.model.Faculty;
public interface FacultyRepository extends JpaRepository<Faculty,Long> {

}
