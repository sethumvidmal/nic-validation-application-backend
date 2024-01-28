package org.example.controllers;

import org.example.dto.AdminDTO;
import org.example.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class AdminController {
    final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("register-admin")
    public void saveAdmin(@RequestBody AdminDTO adminDTO){
        adminService.saveAdmin(adminDTO);
    }

    @GetMapping("/{name}/{password}")
    public List<AdminDTO> searchAdmin(@PathVariable String name, @PathVariable String password){
        return adminService.searchAdmin(name,password);
    }
}
