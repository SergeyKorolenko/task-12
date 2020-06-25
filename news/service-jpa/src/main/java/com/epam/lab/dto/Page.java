package com.epam.lab.dto;

import java.util.List;

public class Page<T> {

    private List<T> elements;
    private Pageable pageable;

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
}
