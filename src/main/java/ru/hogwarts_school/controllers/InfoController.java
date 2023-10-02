package ru.hogwarts_school.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.net.UnknownHostException;

@RestController
@RequestMapping("/info")
public class InfoController {
    //    @Autowired
//    private ServerProperties serverProperties;
//
//    @GetMapping("/getPort")
//    public int port() throws UnknownHostException {
//
//        int port = serverProperties.getPort();
//        return port;
//    }
    @Value("${server.port}")
    private String port;

    @GetMapping("/getPort")
    public String getPort() {
        return port;
    }
}
