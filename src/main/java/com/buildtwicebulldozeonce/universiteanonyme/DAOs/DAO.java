package com.buildtwicebulldozeonce.universiteanonyme.DAOs;

import java.util.List;

public interface DAO<T> {
    List<T> getAll();
    T getById(int id);
}
