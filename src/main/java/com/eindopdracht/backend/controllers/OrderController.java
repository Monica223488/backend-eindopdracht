package com.eindopdracht.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private String text;

    @GetMapping("/orders")
    public String sayHello() {
        return "Hi there";
    }
    @PostMapping("/orders")
        public String saveOrder(@RequestParam String text) {
        this.text = text;
        return "Done";
    }
}
