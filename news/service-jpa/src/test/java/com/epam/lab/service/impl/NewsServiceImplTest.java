package com.epam.lab.service.impl;

import com.epam.lab.dto.*;
import com.epam.lab.model.Author;
import com.epam.lab.model.News;
import com.epam.lab.model.SearchCriteria;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.impl.NewsRepositoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class NewsServiceImplTest {

    private static NewsRepositoryImpl newsRepository;

    private static NewsServiceImpl newsService;

    @Before
    public void setUp() {
        newsRepository = mock(NewsRepositoryImpl.class);
        newsService = new NewsServiceImpl(new ModelMapper(), newsRepository);
    }

    @Test
    public void readTest() {
        News news = new News();
        news.setId(1L);
        news.setTitle("test title");
        news.setShortText("short text");
        news.setFullText("fulltext");

        Author author = new Author();
        author.setId(1L);
        author.setName("test name");
        author.setSurname("test surname");

        news.setAuthor(author);

        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("tag name");

        news.getTagSet().add(tag);

        when(newsRepository.read(1L)).thenReturn(news);

        NewsDto expected = new NewsDto();
        expected.setId(1L);
        expected.setTitle("test title");
        expected.setShortText("short text");
        expected.setFullText("fulltext");

        AuthorDto expectedAuthor = new AuthorDto();
        expectedAuthor.setId(1L);
        expectedAuthor.setName("test name");
        expectedAuthor.setSurname("test surname");
        expected.setAuthor(expectedAuthor);

        TagDto expectedTag = new TagDto();
        expectedTag.setId(1L);
        expectedTag.setName("tag name");

        expected.getTagSet().add(expectedTag);

        NewsDto actual = newsService.read(1L);

        assertEquals(expected, actual);
        verify(newsRepository).read(1L);
    }

    @Test
    public void deleteTest() {
        newsService.delete(anyLong());
        verify(newsRepository).delete(anyLong());
    }

    @Test
    public void updateTest() {
        newsService.update(new NewsDto());
        verify(newsRepository).update(any(News.class));
    }

    @Test
    public void createTest() {
        newsService.create(new NewsDto());
        verify(newsRepository).create(any(News.class));
    }

    @Test
    public void getCountTest() {
        long expected = 20L;

        when(newsRepository.getCount()).thenReturn(20L);

        long actual = newsService.getCount();

        assertEquals(expected, actual);
        verify(newsRepository).getCount();
    }

    @Test
    public void readAllTest() {
        News first = new News();
        first.setId(1L);
        first.setTitle("test title 1");
        first.setShortText("short text 1");
        first.setFullText("fulltext 1");

        Author firstAuthor = new Author();
        firstAuthor.setId(1L);
        firstAuthor.setName("test name 1");
        firstAuthor.setSurname("test surname 1");

        first.setAuthor(firstAuthor);

        Tag firstTag = new Tag();
        firstTag.setId(1L);
        firstTag.setName("tag name 1");

        first.getTagSet().add(firstTag);

        News second = new News();
        second.setId(2L);
        second.setTitle("test title 2");
        second.setShortText("short text 2");
        second.setFullText("fulltext 2");

        Author secondAuthor = new Author();
        secondAuthor.setId(2L);
        secondAuthor.setName("test name 2");
        secondAuthor.setSurname("test surname 2");

        second.setAuthor(secondAuthor);

        Tag secondTag = new Tag();
        secondTag.setId(2L);
        secondTag.setName("tag name 2");

        second.getTagSet().add(secondTag);

        List<News> news = Stream.of(first, second).collect(Collectors.toList());

        when(newsRepository.readAll()).thenReturn(news);

        NewsDto firstExpected = new NewsDto();
        firstExpected.setId(1L);
        firstExpected.setTitle("test title 1");
        firstExpected.setShortText("short text 1");
        firstExpected.setFullText("fulltext 1");

        AuthorDto firstAuthorExpected = new AuthorDto();
        firstAuthorExpected.setId(1L);
        firstAuthorExpected.setName("test name 1");
        firstAuthorExpected.setSurname("test surname 1");

        firstExpected.setAuthor(firstAuthorExpected);

        TagDto firstTagExpected = new TagDto();
        firstTagExpected.setId(1L);
        firstTagExpected.setName("tag name 1");

        firstExpected.getTagSet().add(firstTagExpected);

        NewsDto secondExpected = new NewsDto();
        secondExpected.setId(2L);
        secondExpected.setTitle("test title 2");
        secondExpected.setShortText("short text 2");
        secondExpected.setFullText("fulltext 2");

        AuthorDto secondAuthorExpected = new AuthorDto();
        secondAuthorExpected.setId(2L);
        secondAuthorExpected.setName("test name 2");
        secondAuthorExpected.setSurname("test surname 2");

        secondExpected.setAuthor(secondAuthorExpected);

        TagDto secondTagExpected = new TagDto();
        secondTagExpected.setId(2L);
        secondTagExpected.setName("tag name 2");

        secondExpected.getTagSet().add(secondTagExpected);

        List<NewsDto> expected = Stream.of(firstExpected, secondExpected).collect(Collectors.toList());

        List<NewsDto> actual = newsService.readAll();

        assertEquals(expected, actual);

        verify(newsRepository).readAll();
    }

    @Test
    public void searchByCriteriaTest() {
        News first = new News();
        first.setId(1L);
        first.setTitle("test title");
        first.setShortText("short text");
        first.setFullText("fulltext");

        Author firstAuthor = new Author();
        firstAuthor.setId(1L);
        firstAuthor.setName("test name");
        firstAuthor.setSurname("test surname");

        first.setAuthor(firstAuthor);

        Tag firstTag = new Tag();
        firstTag.setId(1L);
        firstTag.setName("tag name");

        first.getTagSet().add(firstTag);

        List<News> news = Stream.of(first).collect(Collectors.toList());

        when(newsRepository.searchByCriteria(any(SearchCriteria.class))).thenReturn(news);

        NewsDto firstExpected = new NewsDto();
        firstExpected.setId(1L);
        firstExpected.setTitle("test title");
        firstExpected.setShortText("short text");
        firstExpected.setFullText("fulltext");

        AuthorDto expectedAuthor = new AuthorDto();
        expectedAuthor.setId(1L);
        expectedAuthor.setName("test name");
        expectedAuthor.setSurname("test surname");
        firstExpected.setAuthor(expectedAuthor);

        TagDto expectedTag = new TagDto();
        expectedTag.setId(1L);
        expectedTag.setName("tag name");

        firstExpected.getTagSet().add(expectedTag);

        List<NewsDto> expected = Stream.of(firstExpected).collect(Collectors.toList());

        Page<NewsDto> actual = newsService.searchByCriteria(new SearchCriteriaDto(), new Pageable());

        assertEquals(expected, actual.getElements());
        verify(newsRepository).searchByCriteria(any(SearchCriteria.class));
    }
}
