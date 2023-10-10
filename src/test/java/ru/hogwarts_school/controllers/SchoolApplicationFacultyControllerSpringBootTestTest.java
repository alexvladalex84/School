package ru.hogwarts_school.controllers;

import liquibase.Liquibase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts_school.SchoolApplication;
import ru.hogwarts_school.model.Faculty;
import ru.hogwarts_school.model.Student;

import java.nio.file.Path;
import java.util.Collection;


@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SchoolApplicationFacultyControllerSpringBootTestTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Value("${spring.liquibase.change-log}")
    private Path path;
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Test
    public void contextLoads() throws Exception {             //контроллер создался и существует

        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test//тест crud запросов
    public void crud() {
        Faculty faculty = new Faculty();
        faculty.setName("faculty");
        faculty.setColor("red");
//create   post запрос
        ResponseEntity<Faculty> responseCreate = restTemplate.postForEntity("/faculty", faculty, Faculty.class);//Student.class возвращаемый класс
        Assertions.assertThat(responseCreate).isNotNull();
        Assertions.assertThat(responseCreate.getStatusCode()).isEqualTo(HttpStatus.OK);//проверяем статус 200(ОК это 200)
        Faculty respBody = responseCreate.getBody();// переменная с телом запроса
        Assertions.assertThat(respBody).isNotNull();
        Assertions.assertThat(respBody.getId()).isNotNull();
        Assertions.assertThat(respBody.getName()).isNotNull();

        Assertions.assertThat(respBody.getName()).isEqualTo("faculty");
        Assertions.assertThat(respBody.getColor()).isEqualTo("red");
//read  get запрос
        ResponseEntity<Faculty> responseGet = restTemplate.getForEntity("/faculty/" + respBody.getId(), Faculty.class);
        Assertions.assertThat(responseGet).isNotNull();
        Assertions.assertThat(responseGet.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty respBody1 = responseGet.getBody();// переменная с телом запроса
        Assertions.assertThat(respBody1).isNotNull();
        Assertions.assertThat(respBody1.getId()).isNotNull();
        Assertions.assertThat(respBody1.getName()).isEqualTo("faculty");
        Assertions.assertThat(respBody1.getColor()).isEqualTo("red");
//  update  put запрос


        Faculty facultyCheng = new Faculty();

        facultyCheng.setName("facultyCheng");
        facultyCheng.setColor("Green");

        restTemplate.put("/faculty/" + respBody.getId(), facultyCheng);

        ResponseEntity<Faculty> responseGet1 = restTemplate.getForEntity("/faculty/" + respBody.getId(), Faculty.class);
        Assertions.assertThat(responseGet1).isNotNull();
        Assertions.assertThat(responseGet1.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty respBody2 = responseGet1.getBody();// переменная с телом запроса
        Assertions.assertThat(respBody2).isNotNull();
        Assertions.assertThat(respBody2.getId()).isNotNull();
        Assertions.assertThat(respBody2.getName()).isEqualTo("facultyCheng");
        Assertions.assertThat(respBody2.getColor()).isEqualTo("Green");
//  delete  запрос
        restTemplate.delete("/faculty?id=" + respBody.getId());


        ResponseEntity<Faculty> responseGetForDelete = restTemplate.getForEntity("/faculty/" + respBody.getId(), Faculty.class);

        Assertions.assertThat(responseGetForDelete).isNotNull();
        Assertions.assertThat(responseGetForDelete.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);


    }

    @Test
    public void findByNameContainsIgnoreCaseOrColorContainsIgnoreCase() {
        Faculty faculty = new Faculty();
        faculty.setName("faculty");
        faculty.setColor("Green");

        ResponseEntity<Faculty> responseCreate = restTemplate.postForEntity("/faculty", faculty, Faculty.class);
        Assertions.assertThat(responseCreate).isNotNull();
        Assertions.assertThat(responseCreate.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseCreate.getBody().getName()).isEqualTo("faculty");


        ResponseEntity<Collection> responseGet = restTemplate.getForEntity("/faculty/name-or-color?param=" + "fac", Collection.class);
        Assertions.assertThat(responseGet).isNotNull();
        Assertions.assertThat(responseGet.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseGet).isNotNull();


    }


    @Test
    public void studentByIdFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("faculty");
        faculty.setColor("red");

        ResponseEntity<Faculty> responseCreateFaculty = restTemplate.postForEntity("/faculty", faculty, Faculty.class);
        Assertions.assertThat(responseCreateFaculty).isNotNull();
        Assertions.assertThat(responseCreateFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty respBodyFa = responseCreateFaculty.getBody();
        Long facultyId = responseCreateFaculty.getBody().getId();

        Student student = new Student();
        student.setName("Bob");
        student.setAge(20);
        student.setFaculty(respBodyFa);

        ResponseEntity<Student> responseCreateStudent = restTemplate.postForEntity("/student", student, Student.class);
        Assertions.assertThat(responseCreateStudent).isNotNull();
        Assertions.assertThat(responseCreateStudent.getStatusCode()).isEqualTo(HttpStatus.OK);


        ResponseEntity<Collection> responseGet = restTemplate.getForEntity("/faculty/student-byIdFaculty?id="
                + facultyId, Collection.class);
        Assertions.assertThat(responseGet).isNotNull();
        Assertions.assertThat(responseGet.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseGet.getBody()).isNotNull();

    }


}
