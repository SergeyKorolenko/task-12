package com.epam.lab.model;

import java.util.HashSet;
import java.util.Set;

public class SearchCriteria {

    private String name;
    private String surname;
    private Set<String> tags = new HashSet<>();
    private Set<String> sortBy = new HashSet<>();

    private int page;
    private int size;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Set<String> getSortBy() {
        return sortBy;
    }

    public void setSortBy(Set<String> sortBy) {
        this.sortBy = sortBy;
    }
}
