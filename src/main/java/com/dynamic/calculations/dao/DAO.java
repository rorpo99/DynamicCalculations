package com.dynamic.calculations.dao;

import java.util.List;

public interface DAO<T> {
    List<T> getAll();

    void create(T t);

    T get(int id);

    void update(T t, int id);

    void delete(int id);
}
