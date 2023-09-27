package ru.hogwarts_school.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebMvcTest(FacultyController.class)
public class SchoolApplicationFacultyControllerWithMockTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
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
    private FacultyController studentController;

    @Test//тест crud запросов
    public void crud() throws Exception {
        Faculty faculty1 = new Faculty();
        faculty1.setId(1l);
        faculty1.setName("Faculty1");
        faculty1.setColor("red");

        Faculty faculty2 = new Faculty();
        faculty2.setId(2l);
        faculty2.setName("Faculty2");
        faculty2.setColor("blue");

        Faculty faculty3 = new Faculty();
        faculty3.setId(3l);
        faculty3.setName("Faculty3");
        faculty3.setColor("bleak");


//create   post запрос
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty1);

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                        .content(objectMapper.writeValueAsString(faculty1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Faculty1"))
                .andExpect(jsonPath("$.color").value("red"));

//read  get запрос
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty1));//вернуть по id

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/1")
                        .content(objectMapper.writeValueAsString(faculty1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Faculty1"))
                .andExpect(jsonPath("$.color").value("red"));


        when(facultyRepository.findAll()).thenReturn(List.of(faculty1,faculty2,faculty3)); //вернуть всех

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty")
                        .content(objectMapper.writeValueAsString(faculty1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].name").value("Faculty2"))
                .andExpect(jsonPath("$[2].color").value("bleak"));


        when(facultyRepository.findAll()).thenReturn(List.of(faculty1,faculty2));
        when(facultyRepository.findByNameContainsIgnoreCase(any(String.class))).thenReturn(List.of(faculty1, faculty2));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty?name=Faculty1")
                        .content(objectMapper.writeValueAsString(faculty1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Faculty1"))
                .andExpect(jsonPath("$[0].color").value("red"));

        when(facultyRepository.findByColorContainsIgnoreCase(any(String.class))).thenReturn(List.of(faculty1,faculty2));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty?color=re")
                        .content(objectMapper.writeValueAsString(faculty1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Faculty1"))
                .andExpect(jsonPath("$[0].color").value("red"));


        when(facultyRepository.findByNameContainsIgnoreCaseOrColorContainsIgnoreCase(any(String.class), any(String.class)))
                .thenReturn(List.of(faculty1,faculty2,faculty3));
        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/name-or-color?param=bl")
                        .content(objectMapper.writeValueAsString(faculty1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())

                .andExpect(jsonPath("$[1].color").value("blue"))
                .andExpect(jsonPath("$[2].color").value("bleak"));

//  update  put запрос


        when(facultyRepository.getById(any(Long.class))).thenReturn(faculty1);
        mockMvc.perform(
                        put("/faculty/1", faculty1.getId())
                                .content(objectMapper.writeValueAsString(new Faculty(0l,"Faculty4", "yellow")))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("Faculty4"));


//  delete  запрос


        mockMvc.perform(
                        delete("/faculty?id=1", faculty1.getId()))
                .andExpect(status().isOk());

    }

    @Test
    void studentByIdFaculty() throws Exception {
        Faculty faculty1 = new Faculty();
        faculty1.setId(1l);
        faculty1.setName("Faculty1");
        faculty1.setColor("red");

        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Bob");
        student1.setAge(20);
        student1.setFaculty(faculty1);

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Alex");
        student2.setAge(21);
        student2.setFaculty(faculty1);

        when( studentRepository.findByFacultyId(any(Long.class))).thenReturn(List.of(student1,student2));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/student-byIdFaculty?id="+1)
                        .content(objectMapper.writeValueAsString(faculty1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Bob"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[1].id").value(2));
    }


}
