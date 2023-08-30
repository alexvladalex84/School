package ru.hogwarts_school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.hogwarts_school.exceptions.FacultyDoesNotExistException;
import ru.hogwarts_school.model.Faculty;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FacultyServiceTest {
    FacultyService facultyService = new FacultyService();

    @Test
    void addFaculty() {
        //        входящие данные
        long ID = 3;
        String NAME = "faculty";
        String COLOR = "red";
        //        ожидаемый результат
        Faculty expected = new Faculty(ID, NAME, COLOR);

        //        начало теста
        Faculty addFaculty = facultyService.addFaculty(NAME, COLOR);
        assertEquals(expected, addFaculty);


    }

    @Test
    void findFaculty() {
        //        входящие данные
        long ID = 2;
        long ID2 = 10;
        String NAME = "faculty";
        String COLOR = "red";
        Faculty faculty = new Faculty(ID, NAME, COLOR);
        facultyService.addFaculty(NAME, COLOR);
        //        ожидаемый результат
        Faculty expected = new Faculty(ID, NAME, COLOR);
//        String expectedMassage = "Факультета с таким именем не существует";


        //        начало теста
        Faculty addFaculty = facultyService.findFaculty(ID);
        assertEquals(expected, addFaculty);
//        Exception exception = Assertions.assertThrows(FacultyDoesNotExistException.class, () -> facultyService.findFaculty(ID2));
//        Assertions.assertEquals(expectedMassage, exception.getMessage());


    }


    @Test
    void delete() {
        //        входящие данные
        long ID = 1;
        long ID2 = 10;
        String NAME = "facultyD";
        String COLOR = "redD";
        facultyService.addFaculty(NAME, COLOR);
        //        ожидаемый результат
        Faculty expected = new Faculty(ID, NAME, COLOR);
        String expectedMassage = "Факультета с таким именем не существует";


        //        начало теста
        Faculty addStudent = facultyService.delete(ID);
        assertEquals(expected, addStudent);
        Exception exception = Assertions.assertThrows(FacultyDoesNotExistException.class, () -> facultyService.delete(ID2));
        Assertions.assertEquals(expectedMassage, exception.getMessage());

    }

    @Test
    void getAllByColor() {
        long ID = 4;
        long ID2 = 10;
        String NAME = "facultyD";
        String COLOR = "redD";
        facultyService.addFaculty(NAME, COLOR);
        Faculty faculty = new Faculty(ID, NAME, COLOR);
        //        ожидаемый результат
        List<Faculty> expected = new ArrayList<>(List.of(faculty));
//        String expectedMassage = "Студента с таким именем не существует";


        //        начало теста
        List<Faculty> actualList = facultyService.getAllByColor(COLOR);
        assertEquals(expected, actualList);


    }

    @Test
    void getFacultyMap() {
    }
}