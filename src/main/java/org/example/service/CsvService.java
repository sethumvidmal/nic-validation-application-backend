package org.example.service;

import org.example.dao.CsvDao;
import org.example.dto.CsvDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CsvService {

    Iterable<CsvDao> getAllNicDetails();

    void saveCsv(MultipartFile file);

    Iterable<CsvDao> getByGender(String gender);
}
