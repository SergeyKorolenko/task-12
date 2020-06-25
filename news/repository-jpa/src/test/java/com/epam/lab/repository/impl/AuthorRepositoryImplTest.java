package com.epam.lab.repository.impl;

import com.epam.lab.configuration.DataSourceConfig;
import com.epam.lab.exception.DataNotFoundException;
import com.epam.lab.exception.DuplicateDataException;
import com.epam.lab.model.Author;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.repository.NewsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataSourceConfig.class)
public class AuthorRepositoryImplTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Test
    public void readAllTest() {
        assertEquals(20, authorRepository.readAll().size());
    }

    @Test
    public void readTest() {
        Author expected = new Author();
        expected.setId(1L);
        expected.setName("Sergei");
        expected.setSurname("Crachev");

        Author actual = authorRepository.read(1L);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getSurname(), actual.getSurname());
    }

    @Test(expected = DataNotFoundException.class)
    public void negativeReadTest() {
        authorRepository.read(21L);
    }

    @Test
    @Rollback
    @Transactional
    public void deleteTest() {
        authorRepository.delete(3L);
        assertEquals(19, authorRepository.readAll().size());
    }

    @Test(expected = DataNotFoundException.class)
    public void negativeDeleteTest() {
        authorRepository.delete(32L);
    }

    @Test
    @Rollback
    @Transactional
    public void createTest() {
        Author author = new Author();
        author.setName("new name");
        author.setSurname("new surname");

        long actualId = authorRepository.create(author);

        assertEquals(21L, actualId);
    }

    @Test(expected = DuplicateDataException.class)
    public void negativeCreateTest() {
        Author author = new Author();
        author.setId(1L);

        authorRepository.create(author);

        author.setId(1L);
        author.setName("new name");
        author.setSurname("new surname");

        authorRepository.create(author);

    }

    @Test
    @Rollback
    @Transactional
    public void updateTest() {
        Author expected = new Author();
        expected.setId(1L);
        expected.setName("Sergei update");
        expected.setSurname("Crachev");

        authorRepository.update(expected);

        Author actual = authorRepository.read(1L);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getSurname(), actual.getSurname());
    }
}
