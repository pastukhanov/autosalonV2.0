package com.ppe.app.autosalon.repository;

import java.util.Map;

public interface RepositoryObjectCreator<T> {
    T createObject(Map<String, Object> map);
}