package ru.hogwarts_school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts_school.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentServiceTest {
    StudentService studentService = new StudentService();


    @Test
    void addStudent() {
        //        входящие данные
        long ID = 5;
        String NAME = "Ivan";
        int AGE = 25;
        //        ожидаемый результат
        Student expected = new Student(ID, NAME, AGE);
//        String expectedMassage = "Попытка добавить уже существующего студента!";


        //        начало теста
        Student addStudent = studentService.addStudent(NAME, AGE);
        assertEquals(expected, addStudent);
//        Exception exception = Assertions.assertThrows(ArrayIsFullException.class, () -> studentService.addStudent(name,age));
//        Assertions.assertEquals(expectedMassage, exception.getMessage());

    }

    @Test
    void findStudent() {
        //        входящие данные
        long ID = 3;
        long ID2 = 2;
        String NAME = "Alex";
        int AGE = 30;
        fullStudent();
        //        ожидаемый результат
        Student expected = new Student(ID, NAME, AGE);
//        String expectedMassage = "Студента с таким именем не существует";


        //        начало теста
        Student actual = studentService.findStudent(ID);
        assertEquals(expected, actual);
//        Exception exception = Assertions.assertThrows(StudentDoesNotExistException.class, () -> studentService.findStudent(ID2));
//        Assertions.assertEquals(expectedMassage, exception.getMessage());

    }


//    @Test
//    void editStudent() {
//    }

    @Test
    void delete() {
        //        входящие данные
        long ID = 1;
        long ID2 = 2;
        String NAME = "Alex";
        int AGE = 30;
        fullStudent();
        //        ожидаемый результат
        Student expected = new Student(ID, NAME, AGE);
//        String expectedMassage = "Студента с таким именем не существует";


        //        начало теста
        Student addStudent = studentService.delete(ID);
        assertEquals(expected, addStudent);
//        Exception exception = Assertions.assertThrows(StudentDoesNotExistException.class, () -> studentService.delete(ID2));
//        Assertions.assertEquals(expectedMassage, exception.getMessage());

    }

    @Test
    void getAllByAge() {
        //        входящие данные
        long ID = 4;
        String NAME = "Alex";
        int AGE = 30;
        fullStudent();

        Student student = new Student(ID, NAME, AGE);
        //        ожидаемый результат
        List<Student> expected = new ArrayList<>(List.of(student));
//        String expectedMassage = "Студента с таким именем не существует";


        //        начало теста
        List<Student> actualList = studentService.getAllByAge(AGE);
        assertEquals(expected, actualList);
//        Exception exception = Assertions.assertThrows(StudentDoesNotExistException.class, () -> studentService.delete(ID2));
//        Assertions.assertEquals(expectedMassage, exception.getMessage());


    }

    @Test
    void getStudentMap() {
        //        входящие данные
        long ID = 2;
        String NAME = "Alex";
        int AGE = 30;
        Student student = new Student(ID, NAME, AGE);
        fullStudent();
        //        ожидаемый результат
        Map<Long, Student> expected = new HashMap<>();
        expected.put(ID, student);
        //        начало теста
        Map<Long, Student> studentMap = studentService.getStudentMap();
        assertEquals(expected, studentMap);

    }

    private void fullStudent() {
        studentService.addStudent("Alex", 30);


    }

    private void cleanStudent() {
        long id = 1;
        studentService.delete(id);


    }

}