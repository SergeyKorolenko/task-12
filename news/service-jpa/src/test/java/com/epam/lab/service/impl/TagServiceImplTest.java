package com.epam.lab.service.impl;

import com.epam.lab.dto.TagDto;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.impl.TagRepositoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TagServiceImplTest {

    private static TagRepositoryImpl tagRepository;

    private static TagServiceImpl tagService;

    @Before
    public void setUp() {
        tagRepository = mock(TagRepositoryImpl.class);
        tagService = new TagServiceImpl(new ModelMapper(), tagRepository);
    }

    @Test
    public void readTest() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("test name");

        when(tagRepository.read(1L)).thenReturn(tag);

        TagDto expected = new TagDto();
        expected.setId(1L);
        expected.setName("test name");

        TagDto actual = tagService.read(1L);

        assertEquals(expected, actual);
        verify(tagRepository).read(1L);
    }

    @Test
    public void updateTest() {
        tagService.update(new TagDto());
        verify(tagRepository).update(any(Tag.class));
    }

    @Test
    public void deleteTest() {
        tagService.delete(anyLong());
        verify(tagRepository).delete(anyLong());
    }

    @Test
    public void createTest() {
        tagService.create(new TagDto());
        verify(tagRepository).create(any(Tag.class));
    }

    @Test
    public void readAllTest() {
        Tag first = new Tag();
        first.setId(1L);
        first.setName("first");

        Tag second = new Tag();
        second.setId(1L);
        second.setName("second");

        Tag third = new Tag();
        third.setId(1L);
        third.setName("third");

        List<Tag> tags = Stream.of(first, second, third).collect(Collectors.toList());

        when(tagRepository.readAll()).thenReturn(tags);

        TagDto firstExpected = new TagDto();
        firstExpected.setId(1L);
        firstExpected.setName("first");

        TagDto secondExpected = new TagDto();
        secondExpected.setId(1L);
        secondExpected.setName("second");

        TagDto thirdExpected = new TagDto();
        thirdExpected.setId(1L);
        thirdExpected.setName("third");

        List<TagDto> expected = Stream.of(firstExpected, secondExpected, thirdExpected).collect(Collectors.toList());

        List<TagDto> actual = tagService.readAll();

        assertEquals(expected, actual);

        verify(tagRepository).readAll();

    }

}
