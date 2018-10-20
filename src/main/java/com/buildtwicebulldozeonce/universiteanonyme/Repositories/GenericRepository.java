package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface GenericRepository<T>
{
    List<T> getAll();
    T getById(int id);
}
