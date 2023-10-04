package ru.hogwarts_school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts_school.exceptions.StudentNotFoundException;
import ru.hogwarts_school.model.Faculty;
import ru.hogwarts_school.model.Student;
import ru.hogwarts_school.repositories.AvatarRepository;
import ru.hogwarts_school.repositories.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentService {

    Logger LOG = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;


    public StudentService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    public Student addStudent(Student student) {
        LOG.info("был вызван метод :addStudent(Student student)");
        return studentRepository.save(student);
    }


    public Student editStudent(Long id, Student student) {
        LOG.info("был вызван метод : editStudent(Long id, Student student)");

        Student st = studentRepository.getById(id);
        st.setName(student.getName());
        st.setAge(student.getAge());

        return studentRepository.save(st);
    }

    public void delete(Long id) {
        LOG.info("был вызван метод : delete(Long id)");
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

    public List<Student> getOfStudentByLetter(String letter) {
        return studentRepository.findAll()
                .stream()
                .filter(i -> i.getName().startsWith(letter))
                .collect(Collectors.toList());

    }

    public Object getStudentAverageAge() {
        return studentRepository.findAll()
                .stream()

                .mapToInt(Student::getAge)
                .average()
                .orElseThrow();
    }

    public ResponseEntity getAllStudent(Long Id, Integer age, String name) {
        LOG.info("был вызван метод : getAllStudent(Long Id, Integer age, String name)");

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
        LOG.info("был вызван метод : getAll()");
        return ResponseEntity.ok(studentRepository.findAll());
    }

    public Student findById(Long id) {
        LOG.info("был вызван метод : findById(Long id)");
        Student student =studentRepository.findById(id).get();
        if (student == null) {
            LOG.info("ошибка:в параметр метода findById(Long id) введён не корректный id");
        }

//        try {
//            throw new StudentNotFoundException("!!!ОШИБКА!!!");
//
//        } catch ( Exception e) {
//            LOG.error("ошибка:в параметр метода findById(Long id) введён не корректный id",e.getMessage());
//        }
//        return studentRepository.findById(id).get();
        return studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);

    }

    public List<Student> findByAgeBetween(int min, int max) {
        LOG.info("был вызван метод : findByAgeBetween(int min, int max)");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getFaculty(Long studentId) {
        LOG.info("был вызван метод : getFaculty(Long studentId)");
        return studentRepository.findById(studentId).get().getFaculty();
    }

    public List<Student> findByFacultyId(long facultyId) {
        LOG.info("был вызван метод : findByFacultyId(long facultyId)");
        return studentRepository.findByFacultyId(facultyId);
    }

    public Integer getCountStudent() {
        LOG.info("был вызван метод : getCountStudent()");
        return     studentRepository.getCountStudent();
    }

    public Double getCountAverageAge() {
        LOG.info("был вызван метод : getCountAverageAge()");
        return studentRepository.getCountAverageAge();
    }

   public List<Student> lastStudents() {
       LOG.info("был вызван метод : lastStudents()");
       return studentRepository.lastStudents();
   }


}
