package org.example.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.CsvDao;
import org.example.repository.CsvRepo;
import org.example.service.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class CsvServiceImpl implements CsvService {

    @Autowired
    private CsvRepo csvRepo;

    @Override
    public void saveCsv(MultipartFile file) {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {

            List<String[]> nic = csvReader.readAll();

            for (String[] row : nic) {

                for (String column : row) {

                    CsvDao entity = validateNic(column);

                    if (null != entity){
                        csvRepo.save(entity);
                    }

                }

            }

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    private CsvDao validateNic(String nic) {

        String bornYear;
        String birthDayOfTheYear;
        String gender = "MALE";

        if (nic.matches("\\d{12}")) {

            bornYear = nic.substring(0, 4);
            birthDayOfTheYear = nic.substring(4, 7);

            if (Integer.parseInt(nic.substring(4, 7)) > 500) {
                gender = "FEMALE";
                birthDayOfTheYear = "" + (Integer.parseInt(nic.substring(4, 7)) - 500);
            }

            String fullDate = bornYear + birthDayOfTheYear;
            LocalDate date = LocalDate.parse(fullDate, DateTimeFormatter.ofPattern("yyyyDDD"));
            LocalDate formattedDate = LocalDate.parse(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            return CsvDao.builder()
                    .age(Period.between(formattedDate, LocalDate.now()).getYears() + "")
                    .dob(formattedDate)
                    .gender(gender)
                    .nic(nic)
                    .build();
        } else {
            return null;
        }
    }
}
