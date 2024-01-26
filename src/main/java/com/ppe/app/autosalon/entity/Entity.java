package com.ppe.app.autosalon.entity;

import java.util.Map;

public interface Entity {
    Map<String, Object> toMap();
    Map<String, Object> toMap(Boolean flag);
    Long getId();


}