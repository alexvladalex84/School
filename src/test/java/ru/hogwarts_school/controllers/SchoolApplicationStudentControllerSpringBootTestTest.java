package ru.hogwarts_school.controllers;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts_school.SchoolApplication;
import ru.hogwarts_school.model.Faculty;
import ru.hogwarts_school.model.Student;

import java.util.Collection;


//import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = SchoolApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationStudentControllerSpringBootTestTest {

    @Autowired
    private TestRestTemplate restTemplate;         //выполняет запросы к приложению
    @LocalServerPort
    private int port;                                 //новый порт на котором будет работать тест приложение


    @Autowired
    private StudentController studentController;   //проверяемый контроллер
//    @Autowired
//    private TestController testController;


    @Test
    public void contextLoads() throws Exception {             //контроллер создался и существует

        Assertions.assertThat(studentController).isNotNull();
//        assertThat(testController).isNotNull();

    }

    //    @Test
//    public void greetings() throws Exception{
//
//                assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/greetings", String.class))
//                .isEqualTo("Hello Student");//expected
//
//    }
    @Test
    public void testGetStudent() throws Exception {

        Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotNull();

    }

    //    @Test
//    public void testPostStudent() throws Exception {
//        Student student = new Student();
//        student.setId(5L);
//        student.setName("Bob");
//        student.setAge(20);
//
//        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student"
//                , student, String.class))
//                .isNotNull();
//    }
    @Test//тест crud запросов
    public void crud() {
        Student student = new Student();

        student.setName("Bob");
        student.setAge(20);
//create   post запрос
        ResponseEntity<Student> responseCreate = restTemplate.postForEntity("/student", student, Student.class);//Student.class возвращаемый класс
        Assertions.assertThat(responseCreate).isNotNull();
        Assertions.assertThat(responseCreate.getStatusCode()).isEqualTo(HttpStatus.OK);//проверяем статус 200(ОК это 200)
        Student respBody = responseCreate.getBody();// переменная с телом запроса
        Assertions.assertThat(respBody).isNotNull();
        Assertions.assertThat(respBody.getId()).isNotNull();
        Assertions.assertThat(respBody.getName()).isNotNull();
        Assertions.assertThat(respBody.getAge()).isNotNull();
        Assertions.assertThat(respBody.getName()).isEqualTo("Bob");
        Assertions.assertThat(respBody.getAge()).isEqualTo(20);
//read  get запрос
        ResponseEntity<Student> responseGet = restTemplate.getForEntity("/student/" + respBody.getId(), Student.class);
        Assertions.assertThat(responseGet).isNotNull();
        Assertions.assertThat(responseGet.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student respBody1 = responseGet.getBody();// переменная с телом запроса
        Assertions.assertThat(respBody1).isNotNull();
        Assertions.assertThat(respBody1.getId()).isNotNull();
        Assertions.assertThat(respBody1.getName()).isEqualTo("Bob");
        Assertions.assertThat(respBody1.getAge()).isEqualTo(20);
//  update  put запрос
        Student studentCheng = new Student();

        studentCheng.setName("Alex");
        studentCheng.setAge(21);
        restTemplate.put("/student/" + respBody.getId(), studentCheng);

        ResponseEntity<Student> responseGetForCheng = restTemplate.getForEntity("/student/" + respBody.getId(), Student.class);
        Assertions.assertThat(responseGetForCheng).isNotNull();
        Assertions.assertThat(responseGetForCheng.getStatusCode()).isEqualTo(HttpStatus.OK);

        Student respBody2 = responseGetForCheng.getBody();// переменная с телом запроса
        Assertions.assertThat(respBody2).isNotNull();
        Assertions.assertThat(respBody2.getId()).isNotNull();

        Assertions.assertThat(respBody2.getName()).isNotNull();

        Assertions.assertThat(respBody2.getName()).isEqualTo("Alex");
        Assertions.assertThat(respBody2.getAge()).isEqualTo(21);
//  delete  запрос
        restTemplate.delete("/student?id=" + respBody.getId());


        ResponseEntity<Student> responseGetForDelete = restTemplate.getForEntity("/student/" + respBody.getId(), Student.class);

        Assertions.assertThat(responseGetForDelete).isNotNull();
        Assertions.assertThat(responseGetForDelete.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);


    }
    /* @GetMapping
    public ResponseEntity getAllStudentByParameters(@RequestParam(required = false) Long Id,
                                                    @RequestParam(required = false) Integer age,
                                                    @RequestParam(required = false) String name

    ) {
        return studentService.getAllStudent(Id, age, name);
    }*/

    @Test
    public void getFacultyByIdStudent() {

        Faculty faculty = new Faculty();
        faculty.setName("faculty1");
        faculty.setColor("red");

        ResponseEntity<Faculty> responseFaculty = restTemplate.postForEntity("/faculty", faculty, Faculty.class);
        Assertions.assertThat(responseFaculty).isNotNull();
        Assertions.assertThat(responseFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);


        Student student = new Student();

        student.setName("Bob");
        student.setAge(25);
        student.setFaculty(responseFaculty.getBody());

        ResponseEntity<Student> responseStudent = restTemplate.postForEntity("/student", student, Student.class);
        Assertions.assertThat(responseStudent).isNotNull();
        Assertions.assertThat(responseStudent.getStatusCode()).isEqualTo(HttpStatus.OK);

        Long studentId = responseStudent.getBody().getId();

        ResponseEntity<Faculty> responseFacultyByStudent = restTemplate
                .getForEntity("/student/faculty-by-idOfStudent?id=" + studentId, Faculty.class);
        Assertions.assertThat(responseFacultyByStudent).isNotNull();
        Assertions.assertThat(responseFacultyByStudent.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(responseFacultyByStudent.getBody()).isNotNull();
        Assertions.assertThat(responseFacultyByStudent.getBody().getId()).isEqualTo(responseFaculty.getBody().getId());


    }

    @Test
    public void testGetByAgeBetween() throws Exception {
        Student student = new Student();

        student.setName("Bob");
        student.setAge(25);
        ResponseEntity<Student> responseStudent = restTemplate.postForEntity("/student", student, Student.class);
        Assertions.assertThat(responseStudent).isNotNull();
        Assertions.assertThat(responseStudent.getStatusCode()).isEqualTo(HttpStatus.OK);


        Student student1 = new Student();

        student.setName("Alex");
        student.setAge(20);
        ResponseEntity<Student> responseStudent1 = restTemplate.postForEntity("/student", student, Student.class);
        Assertions.assertThat(responseStudent1).isNotNull();
        Assertions.assertThat(responseStudent1.getStatusCode()).isEqualTo(HttpStatus.OK);

        Student student2 = new Student();

        student.setName("Max");
        student.setAge(30);
        ResponseEntity<Student> responseStudent2 = restTemplate.postForEntity("/student", student, Student.class);
        Assertions.assertThat(responseStudent2).isNotNull();
        Assertions.assertThat(responseStudent2.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Student> responseGet = restTemplate.getForEntity("/student/" + 3, Student.class);
        Assertions.assertThat(responseGet).isNotNull();
        Assertions.assertThat(responseGet.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student respBody1 = responseGet.getBody();// переменная с телом запроса
        Assertions.assertThat(respBody1).isNotNull();
        Assertions.assertThat(respBody1.getId()).isNotNull();
        Assertions.assertThat(respBody1.getName()).isEqualTo("Max");
        Assertions.assertThat(respBody1.getAge()).isEqualTo(30);


        ResponseEntity<Collection> responseStudentAgeBetween = restTemplate.getForEntity("/student/By-Age-Between?min="
                + 20 + "&max=" + 30, Collection.class);


        Assertions.assertThat(responseStudentAgeBetween).isNotNull();
        Assertions.assertThat(responseStudentAgeBetween.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseStudentAgeBetween.getBody()).isNotNull();
        Assertions.assertThat(responseStudentAgeBetween.getBody());


    }


}