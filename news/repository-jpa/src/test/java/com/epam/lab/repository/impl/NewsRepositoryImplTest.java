package com.epam.lab.repository.impl;

import com.epam.lab.configuration.DataSourceConfig;
import com.epam.lab.exception.DataNotFoundException;
import com.epam.lab.exception.DuplicateDataException;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.SearchCriteria;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.repository.NewsRepository;
import com.epam.lab.repository.TagRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataSourceConfig.class)
public class NewsRepositoryImplTest {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void readTest() {
        News news = newsRepository.read(1L);
        assertEquals("title 1", news.getTitle());
        assertEquals("short text 1", news.getShortText());
        assertEquals("full text 1", news.getFullText());
    }

    @Test(expected = DataNotFoundException.class)
    public void negativeReadTest() {
        newsRepository.read(111L);
    }

    @Test
    public void readAllTest() {
        assertEquals(20, newsRepository.readAll().size());
    }


    @Test
    @Rollback
    @Transactional
    public void createTest() {
        News news = new News();
        news.setTitle("title test");
        news.setShortText("short test");
        news.setFullText("full text");

        Author author = new Author();
        author.setId(1L);
        author.setName("name test");
        author.setSurname("surname test");

        Tag first = new Tag();
        first.setId(1L);
        first.setName("first tag test");

        Tag second = new Tag();
        second.setName("new tag");

        news.setAuthor(author);

        news.getTagSet().add(first);
        news.getTagSet().add(second);

        newsRepository.create(news);

        assertEquals(21, newsRepository.readAll().size());
    }

    @Test(expected = DuplicateDataException.class)
    public void negativeCreateTest() {
        News news = new News();
        news.setTitle("title test");
        news.setShortText("short test");
        news.setFullText("full text");

        Author author = new Author();
        author.setId(1L);
        author.setName("name test");
        author.setSurname("surname test");

        Tag first = new Tag();
        first.setId(1L);
        first.setName("first tag test");

        Tag second = new Tag();
        second.setName("car");

        news.setAuthor(author);

        news.getTagSet().add(first);
        news.getTagSet().add(second);

        newsRepository.create(news);
    }

    @Test
    @Rollback
    @Transactional
    public void delete() {
        newsRepository.delete(1L);
        assertEquals(19, newsRepository.readAll().size());
    }

    @Test(expected = DataNotFoundException.class)
    public void negativeDeleteTest() {
        newsRepository.delete(111L);
    }

    @Test
    @Rollback
    @Transactional
    public void update() {
        News expected = new News();
        expected.setId(1L);
        expected.setTitle("title test hibernate");
        expected.setShortText("short test hibernate");
        expected.setFullText("full text hibernate");

        Author author = new Author();
        author.setId(1L);
        author.setName("name test hibernate");
        author.setSurname("surname test hibernate");

        Tag first = new Tag();
        first.setId(1L);
        first.setName("first tag test hibernate 1");

        Tag second = new Tag();
        second.setName("new tag");

        expected.setAuthor(author);

        expected.getTagSet().add(first);
        expected.getTagSet().add(second);

        newsRepository.update(expected);

        News actual = newsRepository.read(1L);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getShortText(), actual.getShortText());
        assertEquals(expected.getFullText(), actual.getFullText());
    }

    @Test(expected = DuplicateDataException.class)
    public void negativeUpdateTest() {
        News expected = new News();
        expected.setId(1L);
        expected.setTitle("title test hibernate");
        expected.setShortText("short test hibernate");
        expected.setFullText("full text hibernate");

        Author author = new Author();
        author.setId(1L);
        author.setName("name test hibernate");
        author.setSurname("surname test hibernate");

        Tag first = new Tag();
        first.setId(1L);
        first.setName("first tag test hibernate 1");

        Tag second = new Tag();
        second.setName("car");

        expected.setAuthor(author);

        expected.getTagSet().add(first);
        expected.getTagSet().add(second);

        newsRepository.update(expected);
    }


    @Test
    public void getCountTest() {
        assertEquals(20L, (long) newsRepository.getCount());
    }

    @Test
    public void searchByCriteriaWithTagsTest() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setPage(1);
        searchCriteria.setSize(20);
        Set<String> tags = new HashSet<>();
        tags.add("car");
        tags.add("country");
        searchCriteria.setTags(tags);
        List<News> newsList = newsRepository.searchByCriteria(searchCriteria);
        assertEquals(1, newsList.size());
    }

    @Test
    public void searchByCriteriaWithNameTest() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setPage(1);
        searchCriteria.setSize(20);
        searchCriteria.setName("Tolik");
        List<News> newsList = newsRepository.searchByCriteria(searchCriteria);
        assertEquals(1, newsList.size());
    }

    @Test
    public void searchByCriteriaWithSurnameTest() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setSurname("Petrov");
        searchCriteria.setPage(1);
        searchCriteria.setSize(20);
        List<News> newsList = newsRepository.searchByCriteria(searchCriteria);
        assertEquals(1, newsList.size());
    }

    @Test
    public void searchByCriteriaWithOrderByNameTest() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setPage(1);
        searchCriteria.setSize(20);
        Set<String> orderBy = new HashSet<>();
        orderBy.add("NAME");
        searchCriteria.setSortBy(orderBy);
        List<News> newsList = newsRepository.searchByCriteria(searchCriteria);
        assertEquals(20, newsList.size());
    }

    @Test
    public void searchByCriteriaWithOrderBySurnameTest() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setPage(1);
        searchCriteria.setSize(20);
        Set<String> orderBy = new HashSet<>();
        orderBy.add("SURNAME");
        searchCriteria.setSortBy(orderBy);
        List<News> newsList = newsRepository.searchByCriteria(searchCriteria);
        assertEquals(20, newsList.size());
    }

    @Test
    public void searchByCriteriaWithOrderByDateTest() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setPage(1);
        searchCriteria.setSize(20);
        Set<String> orderBy = new HashSet<>();
        orderBy.add("DATE");
        searchCriteria.setSortBy(orderBy);
        List<News> newsList = newsRepository.searchByCriteria(searchCriteria);
        assertEquals(20, newsList.size());
    }

    @Test
    public void searchByCriteriaWithIncorrectParameterTest() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setPage(1);
        searchCriteria.setSize(20);
        Set<String> orderBy = new HashSet<>();
        orderBy.add("INCORRECT");
        searchCriteria.setSortBy(orderBy);
        List<News> newsList = newsRepository.searchByCriteria(searchCriteria);
        assertEquals(20, newsList.size());
    }

    @Test
    public void searchByCriteriaWithEmptyNameTest() {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setPage(1);
        searchCriteria.setSize(20);
        searchCriteria.setName("");
        List<News> newsList = newsRepository.searchByCriteria(searchCriteria);
        assertEquals(20, newsList.size());
    }
}
