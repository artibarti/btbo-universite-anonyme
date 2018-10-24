package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Admin;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.AdminRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<Admin> getAllAdmins() {
        return Lists.newArrayList(adminRepository.findAll());
    }

    public Admin getAdmin(int id) {
        return adminRepository.findById(id).orElse(null);
    }

    public void addAdmin(@NonNull Admin admin) {
        adminRepository.save(admin);
    }
}
