package ru.hogwarts_school.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts_school.model.Student;
import ru.hogwarts_school.repositories.StudentRepository;
import ru.hogwarts_school.service.StudentService;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/TEST")
public class TestController {
    Logger LOG = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public TestController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/greeting")
    public String greetings() {
        return "Hello Student";
    }

    @GetMapping("/formula_for_Stream_parallel")
    public Integer stream() {

        long start = System.currentTimeMillis();

        int sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)

                .parallel()
                .reduce(0, (a, b) -> a + b);

        long finish = System.currentTimeMillis();
        LOG.info("метод выполнился за " + (finish - start));
        return sum;
    }

    /*            parallel
                : метод выполнился за 46
                 : метод выполнился за 16
                : метод выполнился за 17
                : метод выполнился за 16
                : метод выполнился за 13


                без parallel
                 метод выполнился за 22
                 : метод выполнился за 16
                : метод выполнился за 18
                : метод выполнился за 20
              : метод выполнился за 14*/
    @GetMapping("/thread")
    public void printStudentName() {
        List<Student> students = studentRepository.findAll();
        printStudentName(students.get(0));
        printStudentName(students.get(1));
        printStudentName(students.get(2));

        new Thread(() -> {
            printStudentName(students.get(3));
            printStudentName(students.get(4));
            printStudentName(students.get(5));
        }).start();

        new Thread(() -> {
            printStudentName(students.get(6));
            printStudentName(students.get(7));
            printStudentName(students.get(8));
        }).start();

        printStudentName(students.get(9));
        printStudentName(students.get(10));
    }

    private void printStudentName(Student student) {
        System.out.println(Thread.currentThread() + " " + student);
    }

    private final Object object = new Object();

    @GetMapping("/thread_sync")

    public void printStudentNameSync() {
        List<Student> students = studentRepository.findAll();
        printStudentNameSync(students.get(0));
        printStudentNameSync(students.get(1));
        printStudentNameSync(students.get(2));

        new Thread(() -> {
            printStudentNameSync(students.get(3));
            printStudentNameSync(students.get(4));
            printStudentNameSync(students.get(5));
        }).start();

        new Thread(() -> {
            printStudentNameSync(students.get(6));
            printStudentNameSync(students.get(7));
            printStudentNameSync(students.get(8));
        }).start();

        printStudentNameSync(students.get(9));
        printStudentNameSync(students.get(10));
    }

    private void printStudentNameSync(Student student) {
        synchronized (object) {
            System.out.println(Thread.currentThread() + " " + student);
        }

    }
}
