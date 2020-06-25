package com.epam.lab.repository;

import java.util.List;

public interface CrudRepository<T> {

    long create(T entity);

    void update(T entity);

    void delete(long id);

    T read(long id);

    List<T> readAll();

}
