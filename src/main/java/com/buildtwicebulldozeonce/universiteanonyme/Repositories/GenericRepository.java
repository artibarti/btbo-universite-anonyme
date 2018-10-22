package com.buildtwicebulldozeonce.universiteanonyme.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface GenericRepository<T> extends CrudRepository<T, Integer>
{
    List<T> findAll();

    @Override
    Optional<T> findById(Integer integer);
}
