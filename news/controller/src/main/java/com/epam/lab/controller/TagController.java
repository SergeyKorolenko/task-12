package com.epam.lab.controller;

import com.epam.lab.dto.TagDto;
import com.epam.lab.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(value = "/tags")
@Validated
@CrossOrigin(origins = {"http://localhost:3000"})
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagDto> readAll() {
        return tagService.readAll();
    }

    @PostMapping
    public void create(@Valid @RequestBody TagDto tagDto) {
        tagService.create(tagDto);
    }

    @GetMapping(value = "/{id}")
    public TagDto read(@Valid @PathVariable @Positive long id) {
        return tagService.read(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@Valid @PathVariable @Positive long id) {
        tagService.delete(id);
    }

    @PutMapping
    public void update(@Valid @RequestBody TagDto tagDto) {
        tagService.update(tagDto);
    }

}
