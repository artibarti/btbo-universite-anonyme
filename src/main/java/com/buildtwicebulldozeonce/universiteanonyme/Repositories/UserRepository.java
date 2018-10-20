package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import com.buildtwicebulldozeonce.universiteanonyme.Models.User;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserRepository extends GenericRepository<User>, Repository<User, Integer> {
    User getById(int id);
    List<User> getAll();
}
