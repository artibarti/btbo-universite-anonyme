package com.buildtwicebulldozeonce.universiteanonyme.Services;

import com.buildtwicebulldozeonce.universiteanonyme.Models.Permissions;
import com.buildtwicebulldozeonce.universiteanonyme.Repositories.PermissionsRepository;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionsService {

    private final PermissionsRepository permissionsRepository;

    @Autowired
    public PermissionsService(PermissionsRepository permissionsRepository) {
        this.permissionsRepository = permissionsRepository;
    }

    public List<Permissions> getAllPermissions() {
        return Lists.newArrayList(permissionsRepository.findAll());
    }

    public Permissions getPermissions(int id) {
        return permissionsRepository.findById(id).orElse(null);
    }

    public void addPermissions(@NonNull Permissions permissions) {
        permissionsRepository.save(permissions);
    }
}
