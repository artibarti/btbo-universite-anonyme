package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import java.util.List;

public interface GenericRepository<T> {
    List<T> getAll();
    T getById(int id);
}
