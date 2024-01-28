package org.example.service;

import org.example.dto.AdminDTO;

import java.util.List;

public interface AdminService {
    void saveAdmin(AdminDTO adminDTO);

    List<AdminDTO> searchAdmin(String name, String password);
}
