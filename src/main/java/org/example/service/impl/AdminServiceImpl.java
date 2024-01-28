package org.example.service.impl;

import org.example.dao.AdminEntity;
import org.example.dto.AdminDTO;
import org.example.repository.AdminRepository;
import org.example.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    final AdminRepository adminRepository;
    final ModelMapper modelMapper;

    public AdminServiceImpl(AdminRepository adminRepository, ModelMapper modelMapper) {
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveAdmin(AdminDTO adminDTO) {
        adminRepository.save(modelMapper.map(adminDTO, AdminEntity.class));
    }

    @Override
    public List<AdminDTO> searchAdmin(String name, String password) {
        return null;
    }
}
