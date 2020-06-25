package com.epam.lab.model;

public enum OrderByParameter {
    DATE("date"),
    NAME("name"),
    SURNAME("surname");

    private String orderBy;

    OrderByParameter(String orderBy) {
        this.orderBy = orderBy;
    }
}
