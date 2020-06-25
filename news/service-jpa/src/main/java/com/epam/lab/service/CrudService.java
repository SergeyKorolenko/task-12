package com.epam.lab.service;

import java.util.List;

public interface CrudService<T> {

    void create(T entityDto);

    void delete(long id);

    void update(T entityDto);

    T read(long id);

    List<T> readAll();
}
