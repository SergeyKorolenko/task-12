package com.epam.lab.dto;

import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class SearchCriteriaDto {

    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    private String name;
    @Size(min = 2, max = 30, message = "Surname must be between 2 and 30 characters")
    private String surname;

    private Set<@Size(min = 2, max = 30,
            message = "Tag name must be between 2 and 30 characters") String> tags = new HashSet<>();

    private Set<@Size(min = 2, max = 30,
            message = "OrderBy parameter must be between 2 and 30 characters") String> sortBy = new HashSet<>();

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
