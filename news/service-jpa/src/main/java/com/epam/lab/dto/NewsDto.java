package com.epam.lab.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class NewsDto extends AbstractDto {

    @Size(min = 5, max = 30, message = "Title must be between 5 and 30 characters")
    private String title;
    @Size(min = 5, max = 100, message = "Short Text must be between 5 and 100 characters")
    private String shortText;
    @Size(min = 5, max = 2000, message = "Full Text must be between 5 and 2000 characters")
    private String fullText;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate modificationDate;

    @NotNull(message = "Author DTO mustn't be null")
    @Valid
    private AuthorDto author;

    @Valid
    private Set<TagDto> tagSet = new HashSet<>();

    public Set<TagDto> getTagSet() {
        return tagSet;
    }

    public void setTagSet(Set<TagDto> tagSet) {
        this.tagSet = tagSet;
    }

    public AuthorDto getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDto author) {
        this.author = author;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NewsDto newsDto = (NewsDto) o;
        return Objects.equals(title, newsDto.title) &&
                Objects.equals(shortText, newsDto.shortText) &&
                Objects.equals(fullText, newsDto.fullText) &&
                Objects.equals(creationDate, newsDto.creationDate) &&
                Objects.equals(modificationDate, newsDto.modificationDate) &&
                Objects.equals(author, newsDto.author) &&
                Objects.equals(tagSet, newsDto.tagSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, shortText, fullText, creationDate, modificationDate, author, tagSet);
    }
}
