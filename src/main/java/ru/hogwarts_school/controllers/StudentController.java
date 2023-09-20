package ru.hogwarts_school.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts_school.model.Avatar;
import ru.hogwarts_school.model.Faculty;
import ru.hogwarts_school.model.Student;
import ru.hogwarts_school.service.AvatarService;
import ru.hogwarts_school.service.StudentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService,AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }

    @PostMapping
    public Student create(@RequestBody Student student) {

        return studentService.addStudent(student);
    }


        @GetMapping
    public ResponseEntity getAllStudentByParameters(@RequestParam(required = false) Long Id,
                                                    @RequestParam(required = false) Integer age,
                                                    @RequestParam(required = false) String name

    ) {
        return studentService.getAllStudent(Id, age, name);
    }
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.findById(id);
    }

//    @GetMapping
//    public ResponseEntity getAllStudent() {
//        return studentService.getAll();
//    }

    @GetMapping("/By-Age-Between")
    public List<Student> findByAgeBetween(@RequestParam int min, @RequestParam int max) {
        return studentService.findByAgeBetween(min, max);
    }

    @PutMapping("/{id}")
    public Student editStudent(@PathVariable long id,@RequestBody Student student) {
        return studentService.editStudent(id,student);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam Long id) {
        studentService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/faculty-by-idOfStudent")
    public Faculty getFacultyByIdStudent(@RequestParam long id) {
        return studentService.getFaculty(id);
    }



    @PostMapping(value = "{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upLoadAvatar(@PathVariable Long id, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() > 1024 * 300) {
            return ResponseEntity.badRequest().body("Файл слишком большой");
        }
        avatarService.upLoadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{id}/avatar/preview")
    public ResponseEntity<byte[]> downLoadAvatarFromBD(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }



    @GetMapping(value = "{id}/avatar")
    public void downLoadAvatarOrigin(@PathVariable Long id, HttpServletResponse response ) throws IOException{
        Avatar avatar = avatarService.findAvatar(id);

        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream())
        {
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);

        }
    }
}
/*1. Получить факультет студента
2. Получить студентов факультета*/
