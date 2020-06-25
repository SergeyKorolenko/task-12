package com.epam.lab.controller;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(value = "/authors")
@Validated
@CrossOrigin(origins = {"http://localhost:3000"})
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorDto> readAll() {
        return authorService.readAll();
    }

    @PostMapping
    public void create(@Valid @RequestBody AuthorDto authorDto) {
        authorService.create(authorDto);
    }

    @GetMapping(value = "/{id}")
    public AuthorDto read(@Valid @PathVariable @Positive long id) {
        return authorService.read(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@Valid @PathVariable @Positive long id) {
        authorService.delete(id);
    }

    @PutMapping
    public void update(@Valid @RequestBody AuthorDto authorDto) {
        authorService.update(authorDto);
    }
}
