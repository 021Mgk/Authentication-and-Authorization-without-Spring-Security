package com.mgk021.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/s/expert")
public class ExpertController {

    @GetMapping
    public void get(){
        System.out.println("GET");
    }

    @PostMapping
    public void post(){
        System.out.println("POST");
    }


    @PutMapping
    public void put(){
        System.out.println("PUT");
    }
}
