package com.epam.lab.dto;

import javax.validation.constraints.Size;
import java.util.Objects;

public class AuthorDto extends AbstractDto {

    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    private String name;
    @Size(min = 2, max = 30, message = "Surname must be between 2 and 30 characters")
    private String surname;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AuthorDto authorDto = (AuthorDto) o;
        return Objects.equals(name, authorDto.name) &&
                Objects.equals(surname, authorDto.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, surname);
    }
}
