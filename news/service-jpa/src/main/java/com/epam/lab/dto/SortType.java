package com.epam.lab.dto;

public enum SortType {

    NAME("name"), SURNAME("surname"), DATE("date");

    private String type;

    SortType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
