package ru.hogwarts_school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts_school.model.Faculty;

import java.util.List;
@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {


    List<Faculty> findByNameContainsIgnoreCase(String partName);

    List<Faculty> findByColorContainsIgnoreCase(String partColor);

    List<Faculty> findByNameContainsIgnoreCaseOrColorContainsIgnoreCase(String partName, String partColor);
}
