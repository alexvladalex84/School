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




}
