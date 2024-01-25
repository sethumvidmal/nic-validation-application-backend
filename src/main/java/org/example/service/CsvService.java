package org.example.service;

import org.example.dao.CsvDao;
import org.springframework.web.multipart.MultipartFile;

public interface CsvService {

    Iterable<CsvDao> getAllNicDetails();

    void saveCsv(MultipartFile[] files);

    Iterable<CsvDao> getByGender(String gender);
}
