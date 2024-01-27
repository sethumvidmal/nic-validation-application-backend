package org.example.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.CsvDao;
import org.example.service.CsvService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CsvController {

    final CsvService service;

    public CsvController(CsvService service) {
        this.service = service;
    }

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

    @GetMapping("pdf/genarate")
    public void genaratePDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentTime = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=report_" + currentTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        service.generatePdf(response);
    }
}
