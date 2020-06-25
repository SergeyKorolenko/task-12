package com.epam.lab.repository;

import com.epam.lab.model.News;
import com.epam.lab.model.SearchCriteria;

import java.util.List;

public interface NewsRepository extends CrudRepository<News> {

    Long getCount();

    List<News> searchByCriteria(SearchCriteria searchCriteria);
}
