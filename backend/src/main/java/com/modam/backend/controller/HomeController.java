package com.modam.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//임시 홈페이지
@RestController
public class HomeController {
    @GetMapping("/")
    public String hello() {
        return "Welcome to Modam Homepage!";
    }
}
