package com.ppe.app.autosalon.repository;

import java.util.List;

public interface Repository <T> {
    List<T> fetchAll();

    void add(T obj);

    void delete(Long id);

    void update(Long id, T t);

    T findById(Long id);

    Boolean isEmpty();

    Long getLastId();

}
