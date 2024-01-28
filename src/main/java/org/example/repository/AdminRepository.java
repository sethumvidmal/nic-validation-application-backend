package org.example.repository;

import org.example.dao.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    List<AdminEntity> findByUserNameAndPassword(String userName, String password);
}
