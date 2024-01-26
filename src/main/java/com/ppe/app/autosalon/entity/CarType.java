package com.ppe.app.autosalon.entity;

public enum CarType {
    TRUCK("1"),
    SUV("2"),
    SEDAN("3"),
    VAN("4"),
    PASSENGER_CAR("5"),
    UNKNOWN("6");

    private String id;

    CarType (String id) {
        this.id = id;
    }
}
