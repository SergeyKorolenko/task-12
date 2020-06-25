package com.epam.lab.service.impl;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.dto.TagDto;
import com.epam.lab.model.Author;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.impl.AuthorRepositoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AuthorServiceImplTest {

    private static AuthorRepositoryImpl authorRepository;

    private static AuthorServiceImpl authorService;

    @Before
    public void setUp() {
        authorRepository = mock(AuthorRepositoryImpl.class);
        authorService = new AuthorServiceImpl(new ModelMapper(), authorRepository);
    }

    @Test
    public void readTest() {
        Author author = new Author();
        author.setId(1L);
        author.setName("test name");
        author.setSurname("test surname");

        when(authorRepository.read(1L)).thenReturn(author);

        AuthorDto expected = new AuthorDto();
        expected.setId(1L);
        expected.setName("test name");
        expected.setSurname("test surname");

        AuthorDto actual = authorService.read(1L);

        assertEquals(expected, actual);
        verify(authorRepository).read(1L);
    }

    @Test
    public void createTest() {
        authorService.create(new AuthorDto());
        verify(authorRepository).create(any(Author.class));
    }

    @Test
    public void deleteTest() {
        authorService.delete(anyLong());
        verify(authorRepository).delete(anyLong());
    }

    @Test
    public void updateTest() {
        authorService.update(new AuthorDto());
        verify(authorRepository).update(any(Author.class));
    }

    @Test
    public void readAllTest() {
        Author first = new Author();
        first.setId(1L);
        first.setName("test name 1");
        first.setSurname("test surname 1");

        Author second = new Author();
        second.setId(2L);
        second.setName("test name 2");
        second.setSurname("test surname 2");

        List<Author> authors = Stream.of(first, second).collect(Collectors.toList());

        when(authorRepository.readAll()).thenReturn(authors);

        AuthorDto firstExpected = new AuthorDto();
        firstExpected.setId(1L);
        firstExpected.setName("test name 1");
        firstExpected.setSurname("test surname 1");

        AuthorDto secondExpected = new AuthorDto();
        secondExpected.setId(2L);
        secondExpected.setName("test name 2");
        secondExpected.setSurname("test surname 2");

        List<AuthorDto> expected = Stream.of(firstExpected, secondExpected).collect(Collectors.toList());

        List<AuthorDto> actual = authorService.readAll();

        assertEquals(expected, actual);
        verify(authorRepository).readAll();
    }

}
