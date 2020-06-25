package com.epam.lab.controller;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.Page;
import com.epam.lab.dto.Pageable;
import com.epam.lab.dto.SearchCriteriaDto;
import com.epam.lab.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(value = "/news")
@Validated
@CrossOrigin(origins = {"http://localhost:3000"})
public class NewsController {

    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping
    public void create(@Valid @RequestBody NewsDto newsDto) {
        newsService.create(newsDto);
    }

    @GetMapping
    public List<NewsDto> readAll() {
        return newsService.readAll();
    }

    @GetMapping(value = "/{id}")
    public NewsDto read(@Valid @PathVariable @Positive long id) {
        return newsService.read(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@Valid @PathVariable @Positive long id) {
        newsService.delete(id);
    }

    @PutMapping
    public void update(@Valid @RequestBody NewsDto newsDto) {
        newsService.update(newsDto);
    }

    @GetMapping(value = "/search")
    public Page<NewsDto> searchByCriteria(@Valid SearchCriteriaDto searchCriteriaDto, Pageable pageable,
                                          BindingResult bindingResult) {
        return newsService.searchByCriteria(searchCriteriaDto, pageable);
    }

    @GetMapping(value = "/count")
    public long getCount() {
        return newsService.getCount();
    }
}
