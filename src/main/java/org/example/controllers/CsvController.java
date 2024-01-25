package org.example.controllers;

import org.example.dao.CsvDao;
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
    public void saveCsv(@RequestParam("csv") MultipartFile[] files) {
        service.saveCsv(files);
    }

    @GetMapping
    public Iterable<CsvDao> getAllNicDetails() {
        return service.getAllNicDetails();
    }

    @GetMapping("/{gender}")
    public Iterable<CsvDao> getByGender(@PathVariable String gender) {
        return service.getByGender(gender);
    }

}
