package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.CsvDao;
import org.example.service.CsvService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Tag(name = "Nic Validation application")
@RestController
@CrossOrigin
@RequestMapping("/api")
public class CsvController {

    final CsvService service;

    public CsvController(CsvService service) {
        this.service = service;
    }

    @Operation(description = "Upload .csv file")
    @PostMapping
    public void saveCsv(@RequestParam("csv") MultipartFile[] files) {
        service.saveCsv(files);
    }

    @Operation(description = "You can get all nic details here")
    @GetMapping
    public Iterable<CsvDao> getAllNicDetails() {
        return service.getAllNicDetails();
    }

    @Operation(description = "Get all by Gender")
    @GetMapping("/{gender}")
    public Iterable<CsvDao> getByGender(@PathVariable String gender) {
        return service.getByGender(gender);
    }

    @Operation(description = "You can download report as a pdf here")
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

    @Operation(description = "Get male NICs count")
    @GetMapping("/get-male-count")
    public int getMaleCount(){
        return service.getMaleCount();
    }

    @Operation(description = "Get female NICs count")
    @GetMapping("/get-female-count")
    public int getFemaleCount(){
        return service.getFemaleCount();
    }
}
