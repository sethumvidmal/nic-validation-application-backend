package org.example.controllers;

import org.example.service.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CsvController {

    @Autowired
    CsvService service;

    @PostMapping
    public void saveCsv(@RequestParam("csv") MultipartFile file){
        service.saveCsv(file);
    }

}
