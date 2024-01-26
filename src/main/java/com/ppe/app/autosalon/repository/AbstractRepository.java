package com.ppe.app.autosalon.repository;

import com.ppe.app.autosalon.entity.Entity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

abstract public class AbstractRepository<T extends Entity> implements Repository <T> {

    private String dbTableName;
    private Database db;

    protected AbstractRepository(String dbTableName, Database db) {
        this.dbTableName = dbTableName;
        this.db = db;
    }

    @Override
    public List<T> fetchAll() {
        List<T> objs = new ArrayList<>();
        try {
            objs = db.fetchAllInList(dbTableName)
                    .stream()
                    .map(it-> createObject(it))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return  objs;
        }
    }

    protected abstract T createObject(Map<String, Object> map);

    @Override
    public void add(T t) {
        try {
            db.insert(this.dbTableName, t.toMap(false));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("This "+ this.dbTableName + " hasn't been added to db!");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            db.delete(this.dbTableName, id);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("The " + dbTableName + " with the id " + id + " hasn't been deleted!");
        }
    }

    @Override
    public void update(Long id, T t) {
        T tToUpdate = findById(id);
        if (t.equals(tToUpdate)) {
            add(t);
        } else {
            try {
                db.update(dbTableName, t.toMap(false), id);
            } catch (SQLException e) {
                System.out.println("The " + dbTableName + " with the id "+ id + " hasn't been updated!");
            }
        }
    }

    @Override
    public T findById(Long id) {
        return fetchAll().stream()
                .filter(c -> c != null && ((T) c).getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Boolean isEmpty() {
        return fetchAll().isEmpty();
    }

    public Long getAsLong(Object value) {
        return value instanceof Integer ? ((Integer) value).longValue() : (Long) value;
    }

    public Long getLastId() {
        return fetchAll().stream().map(t -> t.getId()).max(Long::compareTo)
                .orElse(0l);
    }

}

