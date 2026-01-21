package com.example.CEMS.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test/hello")
    public String hello() {
        return "Security working fine ";
    }
}
