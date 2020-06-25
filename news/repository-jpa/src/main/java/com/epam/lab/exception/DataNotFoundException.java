package com.epam.lab.exception;

public class DataNotFoundException extends RepositoryException {

    private static final String DATA_NOT_FOUND = "Data not found";

    public DataNotFoundException() {
        this(DATA_NOT_FOUND);
    }

    public DataNotFoundException(String message) {
        super(message);
    }
}
