package ru.hogwarts_school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts_school.exceptions.StudentNotFoundException;
import ru.hogwarts_school.model.Faculty;
import ru.hogwarts_school.model.Student;
import ru.hogwarts_school.repositories.AvatarRepository;
import ru.hogwarts_school.repositories.StudentRepository;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;


    public StudentService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    public Student addStudent(Student student) {

        return studentRepository.save(student);
    }


    public Student editStudent(Long id, Student student) {
        Student st = studentRepository.getById(id);
        st.setName(student.getName());
        st.setAge(student.getAge());

        return studentRepository.save(st);
    }

    public void delete(Long id) {
//     Student student = studentRepository.findById(id).get();
//               Avatar avatar = avatarRepository.findByStudentId(id).get();
//
//        if (avatar.getStudent().equals(student.getId()) ) {
//            studentRepository.deleteById(id);
//            avatarRepository.delete(avatar);
//        }

//avatarRepository.delete(avatar);
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

    public ResponseEntity getAll() {
        return ResponseEntity.ok(studentRepository.findAll());
    }

    public Student findById(Long id) {
//        return studentRepository.findById(id).get();
        return studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
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

    public Integer getCountStudent() {
     return    studentRepository.getCountStudent();
    }

    public Double getCountAverageAge() {
        return studentRepository.getCountAverageAge();
    }

   public List<Student> lastStudents() {
       return studentRepository.lastStudents();
   }


}
