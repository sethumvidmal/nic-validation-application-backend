package org.example.service;

import org.example.dto.CsvDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CsvService {

    void saveCsv(MultipartFile file);

}
