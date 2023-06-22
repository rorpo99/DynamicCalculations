package com.dynamic.calculations.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    List<T> getAll();

    void create(T t);

    T get(int id);

    void update(T t, int id);

    void delete(int id);
}
