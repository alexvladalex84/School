package ru.hogwarts_school.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts_school.service.StudentService;

import java.util.stream.Stream;

@RestController
@RequestMapping("/TEST")
public class TestController {
    Logger LOG = LoggerFactory.getLogger(StudentService.class);
    @GetMapping("/greeting")
    public String greetings() {
        return "Hello Student";
    }

    @GetMapping("/formula_for_Stream_parallel")
    public Integer stream() {
LOG.info("вызван метод stream()");
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b );

        return sum;
    }



}
