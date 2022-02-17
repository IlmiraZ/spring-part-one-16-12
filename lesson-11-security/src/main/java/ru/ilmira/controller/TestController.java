package ru.ilmira.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TestController {
    @GetMapping
    public String homePage() {
        return "redirect:/product";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
