package com.example.minitest.config.service;

import java.util.List;
import java.util.Optional;

public interface ICRUDService<E,T> {
    void save(E e);

    void delete(T id);

    Optional<E> findById(T id);

    List<E> findAll();
}
