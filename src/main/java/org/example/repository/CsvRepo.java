package org.example.repository;

import org.example.dao.CsvDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CsvRepo extends JpaRepository<CsvDao, Long> {

}
