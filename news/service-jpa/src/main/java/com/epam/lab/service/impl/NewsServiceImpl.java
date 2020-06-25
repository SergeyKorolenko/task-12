package com.epam.lab.service.impl;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.Page;
import com.epam.lab.dto.Pageable;
import com.epam.lab.dto.SearchCriteriaDto;
import com.epam.lab.model.News;
import com.epam.lab.model.SearchCriteria;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.service.NewsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    private ModelMapper modelMapper;

    private NewsRepository newsRepository;

    @Autowired
    public NewsServiceImpl(ModelMapper modelMapper, NewsRepository newsRepository) {
        this.modelMapper = modelMapper;
        this.newsRepository = newsRepository;
    }

    @Override
    public void create(NewsDto newsDto) {
        newsRepository.create(modelMapper.map(newsDto, News.class));
    }

    @Override
    public void delete(long id) {
        newsRepository.delete(id);
    }

    @Override
    public void update(NewsDto entityDto) {
        newsRepository.update(modelMapper.map(entityDto, News.class));
    }

    @Override
    public NewsDto read(long id) {
        return modelMapper.map(newsRepository.read(id), NewsDto.class);
    }

    @Override
    public List<NewsDto> readAll() {
        return newsRepository.readAll()
                .stream()
                .map(n -> modelMapper.map(n, NewsDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public Page<NewsDto> searchByCriteria(SearchCriteriaDto searchCriteriaDto, Pageable pageable) {
        SearchCriteria searchCriteria = modelMapper.map(searchCriteriaDto, SearchCriteria.class);
        searchCriteria.setPage(pageable.getPage());
        searchCriteria.setSize(pageable.getSize());
        List<NewsDto> news = newsRepository.searchByCriteria(searchCriteria)
                .stream()
                .map(n -> modelMapper.map(n, NewsDto.class))
                .collect(Collectors.toList());
        Page<NewsDto> newsDtoPage = new Page<>();
        pageable.setTotalElements(searchCriteria.getCount());
        newsDtoPage.setElements(news);
        newsDtoPage.setPageable(pageable);
        return newsDtoPage;
    }

    @Override
    public long getCount() {
        return newsRepository.getCount();
    }
}
