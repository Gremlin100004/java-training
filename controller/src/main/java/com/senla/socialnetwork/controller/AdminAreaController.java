package com.senla.socialnetwork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminAreaController {

    @GetMapping("/admin")
    public String showForm() {
        System.out.println("Pidar work");
        return "sample";
    }
}


