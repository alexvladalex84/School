package ru.hogwarts_school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.hogwarts_school.model.Faculty;
import ru.hogwarts_school.model.Student;
import ru.hogwarts_school.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Test
    void addStudent() {
        // подготовка входных данных
        int faculty1 = 1;
        Long id = 1L;
        String num1 = "Ivan";
        int age = 20;
        Student student1 = new Student(id, num1, age);

// подготовка ожидаемого результата
        when(studentRepository.save(student1)).thenReturn(student1);
        Student expected = student1;
//   начало теста


        Student actualList = studentService.addStudent(student1);
        assertEquals(expected, actualList);
//        assertNotEquals(departmentId1,departmentId2);
        verify(studentRepository).save(student1);
    }


    @Test
    void delete() {
//        // подготовка входных данных
//        int faculty1 = 1;
//        Long id = 1L;
//        String num1 = "Ivan";
//        int age = 20;
//        Student student1 = new Student(id, num1, age);
//        int faculty2 = 2;
//        Long id2 = 2L;
//        String num2 = "Alex";
//        int age2 = 21;
//        Student student2 = new Student(id2, num2, age2);
//        int faculty3 = 3;
//        Long id3 = 3L;
//        String num3 = "Max";
//        int age3 = 22;
//        Student student3 = new Student(id3, num3, age3);
//// подготовка ожидаемого результата
//
//        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1,student3, student2));
////        when(studentRepository.deleteById(id)).thenReturn(List.of());
//        List<Student> expectedList = new ArrayList<>(List.of(student1,student2));
//         ResponseEntity allStudent = studentService.getAllStudent(null,null,null,null);
//        studentService.delete(id3);
//
//
//
////   начало теста
////        Student  actual =  studentService.delete(id3);
//        assertEquals(expectedList,allStudent);
////        assertNotEquals(departmentId1,departmentId2);
////        verify(studentRepository).findByAge(20);
    }

    @Test
    void editStudent() {
        // подготовка входных данных
        int faculty1 = 1;
        Long id = 1L;
        String num1 = "Ivan";
        int age = 20;
        Student student1 = new Student(id, num1, age);

// подготовка ожидаемого результата
        when(studentRepository.save(student1)).thenReturn(student1);
        Student expected = student1;
//   начало теста


        Student actualList = studentService.addStudent(student1);
        assertEquals(expected, actualList);
//        assertNotEquals(departmentId1,departmentId2);
        verify(studentRepository).save(student1);

    }

    @Test
    void getAllStudent() {
        // подготовка входных данных
        int faculty1 = 1;
        Long id = 1L;
        String num1 = "Ivan";
        int age = 20;
        Student student1 = new Student(id, num1, age);
        int faculty2 = 2;
        Long id2 = 2L;
        String num2 = "Alex";
        int age2 = 21;
        Student student2 = new Student(id2, num2, age2);
        int faculty3 = 3;
        Long id3 = 3L;
        String num3 = "Max";
        int age3 = 30;
        Student student3 = new Student(id3, num3, age3);
// подготовка ожидаемого результата

        when(studentRepository.findById(id)).thenReturn(Optional.of(student1));
        ResponseEntity expected1 = ResponseEntity.ok(Optional.of(student1));
        ResponseEntity actual1 = studentService.getAllStudent(id,null,null);


        when(studentRepository.findByAge(age2)).thenReturn(Arrays.asList(student2));
        ResponseEntity expected2 = ResponseEntity.ok(new ArrayList<>(List.of(student2)));
        ResponseEntity actual2 = studentService.getAllStudent(null, age2, null);

        when(studentRepository.findStudentByNameContainsIgnoreCase(num3)).thenReturn(Arrays.asList(student3));
        ResponseEntity expected3 = ResponseEntity.ok(new ArrayList<>(List.of(student3)));
        ResponseEntity actual3 = studentService.getAllStudent(null, null, num3);

        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student3, student2));
        ResponseEntity expected4 = ResponseEntity.ok(new ArrayList<>(List.of(student1, student3, student2)));
        ResponseEntity actual4 = studentService.getAllStudent(null, null, null);


//   начало теста
        assertEquals(actual1, expected1);
        verify(studentRepository).findById(id);
        assertEquals(actual2, expected2);
        verify(studentRepository).findByAge(age2);
        assertEquals(actual3, expected3);
        verify(studentRepository).findStudentByNameContainsIgnoreCase(num3);
        assertEquals(actual4, expected4);
        verify(studentRepository).findAll();
    }




    @Test
    void findByAgeBetween() {
        // подготовка входных данных
        int faculty1 = 1;
        Long id = 1L;
        String num1 = "Ivan";
        int age = 19;
        Student student1 = new Student(id, num1, age);
        int faculty2 = 2;
        Long id2 = 2L;
        String num2 = "Alex";
        int age2 = 21;
        Student student2 = new Student(id2, num2, age2);
        int faculty3 = 3;
        Long id3 = 3L;
        String num3 = "Max";
        int age3 = 25;
        Student student3 = new Student(id3, num3, age3);
// подготовка ожидаемого результата
        when(studentRepository.findByAgeBetween(20, 30)).thenReturn(Arrays.asList(student3, student2));
        List<Student> expectedList = new ArrayList<>(List.of(student3, student2));

//   начало теста
        List<Student> actualList = studentService.findByAgeBetween(20, 30);
        assertEquals(expectedList, actualList);
        verify(studentRepository).findByAgeBetween(20, 30);


    }


    @Test
    void getFaculty() {
//        // подготовка входных данных
//        int faculty = 2;
//        Long studentId = 1L;
//        String num1 = "Ivan";
//        int age = 19;
//        Student student1 = new Student(studentId, num1, age);
//        Long facultyId = 2L;
//        String name = "hogvarts";
//        String color = "red";
//        Faculty faculty1 = new Faculty(facultyId, name, color);
//// подготовка ожидаемого результата
//        when(studentRepository.findById(studentId).get().getFaculty()).thenReturn(faculty1);
//       Faculty expected = faculty1;
//
////   начало теста
//        Faculty actualList = studentService.getFaculty(studentId);
//        assertEquals(expected, actualList);
//        verify(studentRepository).findById(studentId).get().getFaculty();
    }

    @Test
    void findByFacultyId() {
        // подготовка входных данных
        int faculty = 2;
        Long studentId = 1L;
        String num1 = "Ivan";
        int age = 19;
        Student student1 = new Student(studentId, num1, age);
        int faculty2 = 2;
        Long id2 = 2L;
        String num2 = "Alex";
        int age2 = 21;
        Student student2 = new Student(id2, num2, age2);

        long facultyId = 2;
        String name = "hogvarts";
        String color = "red";
        Faculty faculty1 = new Faculty(facultyId, name, color);
// подготовка ожидаемого результата
        when(studentRepository.findByFacultyId(facultyId)).thenReturn(List.of(student1,student2));
       List <Student> expected = new ArrayList<>(List.of(student1,student2));

//   начало теста
        List<Student> actualList = studentService.findByFacultyId(facultyId);
        assertEquals(expected, actualList);
        verify(studentRepository).findByFacultyId(facultyId);
    }
}