package org.example.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.CsvDao;
import org.example.repository.CsvRepo;
import org.example.service.CsvService;
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

    private final CsvRepo csvRepo;

    public CsvServiceImpl(CsvRepo csvRepo) {
        this.csvRepo = csvRepo;
    }

    @Override
    public Iterable<CsvDao> getAllNicDetails() {
        return csvRepo.findAll();
    }

    @Override
    public void saveCsv(MultipartFile[] files) {
        for (MultipartFile file : files) {

            try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {

                List<String[]> nic = csvReader.readAll();

                for (String[] row : nic) {

                    for (String column : row) {

                        CsvDao entity = validateNic(column);

                        if (null != entity) {
                            csvRepo.save(entity);
                        }

                    }

                }

            } catch (IOException | CsvException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Iterable<CsvDao> getByGender(String gender) {
        return csvRepo.findByGender(gender);
    }

    @Override
    public void generatePdf(HttpServletResponse response) throws IOException {
        StringBuilder para = new StringBuilder();
        for (CsvDao csvDao : getAllNicDetails()) {
            para.append(csvDao.toString());
        }
        try (Document document = new Document(PageSize.A4)) {
            PdfWriter.getInstance(document, response.getOutputStream());

            document.open();
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            titleFont.setSize(18);

            Paragraph title = new Paragraph("Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);

            Font contentFont = FontFactory.getFont(FontFactory.HELVETICA);
            contentFont.setSize(12);

            Paragraph content = new Paragraph(String.valueOf(para), contentFont);
            content.setAlignment(Element.ALIGN_LEFT);

            document.add(title);
            document.add(content);
        }
    }

    @Override
    public int getFemaleCount() {
        return csvRepo.countByGender("FEMALE");
    }

    @Override
    public int getMaleCount() {
        return csvRepo.countByGender("MALE");
    }


    private CsvDao validateNic(String nic) {

        String bornYear;
        String birthDayOfTheYear;
        String gender = "MALE";

        if (nic.matches("\\d{12}")) {

            bornYear = nic.substring(0, 4);
            birthDayOfTheYear = nic.substring(4, 7);

            //Female block
            if (Integer.parseInt(nic.substring(4, 7)) > 500) {

                gender = "FEMALE";

                if ((Integer.parseInt(nic.substring(4, 7)) - 500) < 10) {

                    birthDayOfTheYear = "00" + (Integer.parseInt(nic.substring(4, 7)) - 500);

                } else if ((Integer.parseInt(nic.substring(4, 7)) - 500) < 100) {

                    birthDayOfTheYear = "0" + (Integer.parseInt(nic.substring(4, 7)) - 500);

                } else {
                    birthDayOfTheYear = "" + (Integer.parseInt(nic.substring(4, 7)) - 500);
                }
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
