package com.geektrust.backend.repositories;

import java.util.List;
import java.util.Optional;

public interface ICRUDRepository<T,ID> {
    public T save(T entity);
    public List<T> findAll();
    public void delete(T entity);
    public long count();
}
