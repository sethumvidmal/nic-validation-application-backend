package org.example.service;

import jakarta.servlet.http.HttpServletResponse;
import org.example.dao.CsvDao;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CsvService {

    Iterable<CsvDao> getAllNicDetails();

    void saveCsv(MultipartFile[] files);

    Iterable<CsvDao> getByGender(String gender);

    public void generatePdf(HttpServletResponse response) throws IOException;
}
