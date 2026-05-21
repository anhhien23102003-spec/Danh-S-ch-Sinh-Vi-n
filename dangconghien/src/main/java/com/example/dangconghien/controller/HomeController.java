package com.example.dangconghien.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Redirect từ root / đến /students
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/students";
    }
}
