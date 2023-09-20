package ru.hogwarts_school.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts_school.model.Faculty;
import ru.hogwarts_school.model.Student;
import ru.hogwarts_school.repositories.AvatarRepository;
import ru.hogwarts_school.repositories.FacultyRepository;
import ru.hogwarts_school.repositories.StudentRepository;
import ru.hogwarts_school.service.AvatarService;
import ru.hogwarts_school.service.FacultyService;
import ru.hogwarts_school.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class SchoolApplicationStudentControllerWithMockTest {
    @Autowired
    private MockMvc mockMvc;// предназначен для тестирования контроллеров и позволяет тестировать контроллеры без запуска HTTP-сервера
    @Autowired
    private ObjectMapper objectMapper; //для превращения объекта в json объект и обратно
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private StudentService studentService;

    @SpyBean
    private AvatarService avatarService;
    @SpyBean
    private FacultyService facultyService;


    @InjectMocks
    private StudentController studentController;

    @Test//тест crud запросов
    public void crud() throws Exception {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Bob");
        student1.setAge(20);

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Alex");
        student2.setAge(21);

        Student student3 = new Student();
        student3.setId(2L);
        student3.setName("Alex");
        student3.setAge(25);
//create   post запрос
        when(studentRepository.save(any(Student.class))).thenReturn(student1);

        mockMvc.perform(MockMvcRequestBuilders.post("/student")
                        .content(objectMapper.writeValueAsString(student1))//говорит о том что будут приходить вот такие JSON
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())  //.andExpect проверка данных которые вовращются
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Bob"))
                .andExpect(jsonPath("$.age").value(20));

//read  get запрос
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student1));//вернуть по id

        mockMvc.perform(MockMvcRequestBuilders.get("/student/1")
                        .content(objectMapper.writeValueAsString(student1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Bob"))
                .andExpect(jsonPath("$.age").value(20));


        when(studentRepository.findAll()).thenReturn(List.of(student1, student2)); //вернуть всех

        mockMvc.perform(MockMvcRequestBuilders.get("/student")
                        .content(objectMapper.writeValueAsString(student1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].name").value("Alex"))
                .andExpect(jsonPath("$[0].age").value(20));

        when(studentRepository.findByAge(any(Integer.class))).thenReturn(List.of(student1, student2));//вернуть по возрасту

        mockMvc.perform(MockMvcRequestBuilders.get("/student?age=20")
                        .content(objectMapper.writeValueAsString(student1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Bob"))
                .andExpect(jsonPath("$[0].age").value(20));

        when(studentRepository.findStudentByNameContainsIgnoreCase(any(String.class))).thenReturn(List.of(student1, student2));// вернуть по имени

        mockMvc.perform(MockMvcRequestBuilders.get("/student?name=Bo")
                        .content(objectMapper.writeValueAsString(student1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Bob"))
                .andExpect(jsonPath("$[0].age").value(20));


        when(studentRepository.findByAgeBetween(any(int.class), any(int.class))).thenReturn(List.of(student1, student2));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/By-Age-Between?min="
                                + 19 + "&max=" + 23)
                        .content(objectMapper.writeValueAsString(student1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Bob"))
                .andExpect(jsonPath("$[1].name").value("Alex"));
//  update  put запрос


        when(studentRepository.getById(any(Long.class))).thenReturn(student1);
        mockMvc.perform(
                        put("/student/1", student1.getId())
                                .content(objectMapper.writeValueAsString(new Student("Michail", 35)))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Michail"));


//  delete  запрос


        mockMvc.perform(
                        delete("/student?id=1", student1.getId()))
                .andExpect(status().isOk());

    }

    @Test
    public void saveStudentTest() throws Exception {
        Long facultyId = 1l;
        String facultyName = "faculty1";
        String facultyColor = "red";
        Faculty faculty = new Faculty(facultyId, facultyName, facultyColor);

        Long id = 1L;
        String name = "Bob";
        int age = 20;

        //данные которые будем отправлять на сервер
        JSONObject studentObject = new JSONObject();

        studentObject.put("name", name);
        studentObject.put("age", age);

//данные которые должны вернуться
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);


        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders           //.perform делает запрос(mockMvc эмитация вызова andpoint)
                        .post("/student")
                        .content(studentObject.toString())   //передаём тело запроса JSON
                        .contentType(MediaType.APPLICATION_JSON)  //тип запроса который отправляем
                        .accept(MediaType.APPLICATION_JSON))  //тип запроса который принемаем

                .andExpect(status().isOk())  //.andExpect проверка данных которые вовращются
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));


    }


}
