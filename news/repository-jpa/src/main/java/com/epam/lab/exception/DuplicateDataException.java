package com.epam.lab.exception;

public class DuplicateDataException extends RepositoryException {

    private static final String DUPLICATE_TAG = "Duplicate data";

    public DuplicateDataException() {
        this(DUPLICATE_TAG);
    }

    public DuplicateDataException(String message) {
        super(message);
    }
}
