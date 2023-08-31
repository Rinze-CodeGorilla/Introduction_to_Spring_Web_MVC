package com.example.introduction_to_spring_web_mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/api")
public class ApiController {
    @Autowired
    ArrayList<Name> names;

    @GetMapping("/names")
    public ResponseEntity<ArrayList<Name>> namesHandler() {
        return new ResponseEntity<>(names, HttpStatusCode.valueOf(200));
    }
}
