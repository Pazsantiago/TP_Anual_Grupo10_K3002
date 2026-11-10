package org.example.donacionesfrontend.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {
    @GetMapping("/")
        public String index() {
            return "redirect:/home";
        }
    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
