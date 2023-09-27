package ru.hogwarts_school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.hogwarts_school.model.Faculty;
import ru.hogwarts_school.model.Student;
import ru.hogwarts_school.repositories.FacultyRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {
    @InjectMocks
    private FacultyService facultyService;
    @Mock
    private FacultyRepository facultyRepository;
    @Mock
    private StudentService studentService;

    @Test
    void addFaculty() {
        // подготовка входных данных
        long facultyId = 2;
        String name = "hogvarts";
        String color = "red";
        Faculty faculty1 = new Faculty(facultyId, name, color);
// подготовка ожидаемого результата
        when(facultyRepository.save(faculty1)).thenReturn(faculty1);
        Faculty expected = faculty1;
//   начало теста


        Faculty actualList = facultyService.addFaculty(faculty1);
        assertEquals(expected, actualList);
        verify(facultyRepository).save(faculty1);
    }

    @Test
    void editFaculty() {
        // подготовка входных данных
        long facultyId = 2;
        String name = "hogvarts";
        String color = "red";
        Faculty faculty1 = new Faculty(facultyId, name, color);
// подготовка ожидаемого результата
        when(facultyRepository.save(faculty1)).thenReturn(faculty1);
        Faculty expected = faculty1;
//   начало теста


        Faculty actualList = facultyService.addFaculty(faculty1);
        assertEquals(expected, actualList);
//        assertNotEquals(departmentId1,departmentId2);
        verify(facultyRepository).save(faculty1);
    }

    @Test
    void delete() {
    }

    @Test
    void getAllFaculty() {
        // подготовка входных данных
        long facultyId = 1;
        String name = "hogvarts";
        String color = "red";
        Faculty faculty1 = new Faculty(facultyId, name, color);
// подготовка ожидаемого результата

//        when(facultyRepository.findById(facultyId)).thenReturn(Optional.of(faculty1));
//        ResponseEntity expected1 = ResponseEntity.ok(Optional.of(faculty1));
//        ResponseEntity actual1 = facultyService.getAllFaculty(facultyId,null,null);


        when(facultyRepository.findByNameContainsIgnoreCase(name)).thenReturn(Arrays.asList(faculty1));
        ResponseEntity expected2 = ResponseEntity.ok(new ArrayList<>(List.of(faculty1)));
        ResponseEntity actual2 = facultyService.getAllFaculty(null, name, null);

        when(facultyRepository.findByColorContainsIgnoreCase(color)).thenReturn(Arrays.asList(faculty1));
        ResponseEntity expected3 = ResponseEntity.ok(new ArrayList<>(List.of(faculty1)));
        ResponseEntity actual3 = facultyService.getAllFaculty(null, null, color);

        when(facultyRepository.findAll()).thenReturn(Arrays.asList(faculty1));
        ResponseEntity expected4 = ResponseEntity.ok(new ArrayList<>(List.of(faculty1)));
        ResponseEntity actual4 = facultyService.getAllFaculty(null, null, null);


//   начало теста
//        assertEquals(expected1,actual1 );
//        verify(facultyRepository).findById(facultyId);
        assertEquals(actual2, expected2);
        verify(facultyRepository).findByNameContainsIgnoreCase(name);
        assertEquals(actual3, expected3);
        verify(facultyRepository).findByColorContainsIgnoreCase(color);
        assertEquals(actual4, expected4);
        verify(facultyRepository).findAll();
    }

    @Test
    void findByNameContainsIgnoreCaseOrColorContainsIgnoreCase() {
//        // подготовка входных данных
//        long facultyId = 1;
//        String name = "hogvarts";
//        String color = "red";
//        Faculty faculty1 = new Faculty(facultyId, name, color);
//        String param = "re";
//// подготовка ожидаемого результата
//
//
//        when(facultyRepository.findByNameContainsIgnoreCaseOrColorContainsIgnoreCase(name,color)).thenReturn(Arrays.asList(faculty1));
//        List<Faculty> expected2 =new ArrayList<>(List.of(faculty1));
//        List<Faculty> actual2 = facultyService.findByNameContainsIgnoreCaseOrColorContainsIgnoreCase(name);
//
//
////   начало теста
//
//        assertEquals(expected2,actual2);
//        verify(facultyRepository).findByNameContainsIgnoreCase(name);

    }





    @Test
    void getStudentByIdOfFaculty() {
        // подготовка входных данных
        int faculty1 = 1;
        Long id = 1L;
        String num1 = "Ivan";
        int age = 19;
        Student student1 = new Student(id, num1, age);
        int faculty2 = 1;
        Long id2 = 2L;
        String num2 = "Alex";
        int age2 = 21;
        Student student2 = new Student(id2, num2, age2);

        long facultyId = 1;
        String name = "hogvarts";
        String color = "red";
        Faculty faculty = new Faculty(facultyId, name, color);
// подготовка ожидаемого результата
        when(studentService.findByFacultyId(facultyId)).thenReturn(Arrays.asList(student1,student2));
        List<Student> expected =new ArrayList<>(List.of(student1,student2));
        List<Student> actual = facultyService.getStudentByIdOfFaculty(facultyId);
        //   начало теста
        assertEquals(expected,actual);
        verify(studentService).findByFacultyId(facultyId);
    }
}