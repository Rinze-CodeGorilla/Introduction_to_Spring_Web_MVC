package com.example.introduction_to_spring_web_mvc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public record GreetingController (
        Name myName,
        ArrayList<Name> allNames
) {

    // Laat een website adhv een template. Het Model bevat de gegevens waar het template mee mag werken.
    @GetMapping(value = "/greeting")
    public String greeting(@RequestParam(required = false, name = "name", defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping(value = "/stuk")
    public ResponseEntity<String> stuk(@RequestParam String name) {
        return new ResponseEntity<>(Character.toString(name.charAt(100)), HttpStatus.OK);
    }

    // Laat een webpagina zien die met losse HTML is gemaakt en iets uit verschillende beans met verschillende scopes laat zien .
    @GetMapping(value = "/hoi")
    public ResponseEntity<String> hoi(@RequestParam(required = false, name = "name") String name) {
        var sb = new StringBuilder("""
                <!doctype html>
                <html lang="nl">
                <head>
                    <title>Hoi</title>
                    <style>
                        * { outline: 1px dotted #00f3;
                        background: #00f1;
                            margin: 0;}
                   </style>
               </head>
               <body>
                """);
        if (name != null) {
            myName.setName(name);
            sb.append("<h1>Hoi</h1>Welkom hier <em>%s</em>".formatted(name));
        } else if (myName.getName() != null) {
            sb.append("<h1>Hoi %s</h1>".formatted(myName.getName()));
        } else {
            sb.append("<h1>Hoi?</h1>");
        }
        sb.append("<table><tr><th>ID<th>Naam");
        int i = 0;
        for (Name n : allNames) {
            sb.append("<tr><td>%d<td>%s".formatted(++i, n.getName()));
        }
        var headers = new HttpHeaders();
        return new ResponseEntity<>(sb.toString(), headers,HttpStatusCode.valueOf(200));
    }
}
