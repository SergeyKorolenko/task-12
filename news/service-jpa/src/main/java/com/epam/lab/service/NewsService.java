package com.epam.lab.service;

import com.epam.lab.dto.NewsDto;
import com.epam.lab.dto.Page;
import com.epam.lab.dto.Pageable;
import com.epam.lab.dto.SearchCriteriaDto;

import java.util.List;

public interface NewsService extends CrudService<NewsDto> {

    Page<NewsDto> searchByCriteria(SearchCriteriaDto searchCriteriaDto, Pageable pageable);

    long getCount();

}
