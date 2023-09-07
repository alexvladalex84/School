package ru.hogwarts_school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts_school.model.Avatar;
import ru.hogwarts_school.model.Student;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(Long studentId);

}
