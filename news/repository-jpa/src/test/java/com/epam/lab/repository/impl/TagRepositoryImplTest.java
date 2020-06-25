package com.epam.lab.repository.impl;

import com.epam.lab.configuration.DataSourceConfig;
import com.epam.lab.exception.DataNotFoundException;
import com.epam.lab.exception.DuplicateDataException;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.TagRepository;
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
public class TagRepositoryImplTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void readAllTest() {
        assertEquals(20, tagRepository.readAll().size());
    }

    @Test
    public void readTest() {
        Tag expected = new Tag();
        expected.setId(1L);
        expected.setName("animals");

        assertEquals(expected, tagRepository.read(1L));
    }

    @Test(expected = DataNotFoundException.class)
    public void negativeReadTest() {
        tagRepository.read(21L);
    }

    @Test
    @Rollback
    @Transactional
    public void deleteTest() {
        tagRepository.delete(1L);

        assertEquals(19, tagRepository.readAll().size());
    }

    @Test(expected = DataNotFoundException.class)
    public void negativeDeleteTest() {
        tagRepository.delete(111L);
    }

    @Test
    @Rollback
    @Transactional
    public void updateTest() {
        Tag expected = new Tag();
        expected.setId(2L);
        expected.setName("new name");

        tagRepository.update(expected);

        Tag actual = tagRepository.read(2L);

        assertEquals(expected, actual);
    }

    @Test(expected = DuplicateDataException.class)
    public void negativeUpdateTest() {
        Tag expected = new Tag();
        expected.setId(1L);
        expected.setName("car");

        tagRepository.update(expected);
    }

    @Test
    @Rollback
    @Transactional
    public void createTest() {
        Tag first = new Tag();
        first.setName("new tag");

        tagRepository.create(first);

        assertEquals(21, tagRepository.readAll().size());
    }

    @Test(expected = DuplicateDataException.class)
    @Rollback
    public void negativeCreateTest() {
        Tag tag = new Tag();
        tag.setName("animals");

        tagRepository.create(tag);

        tag.setId(1L);
        tag.setName("new tag");

        tagRepository.create(tag);
    }

}
