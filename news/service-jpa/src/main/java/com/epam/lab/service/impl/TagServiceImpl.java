package com.epam.lab.service.impl;

import com.epam.lab.dto.TagDto;
import com.epam.lab.model.Tag;
import com.epam.lab.repository.TagRepository;
import com.epam.lab.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private ModelMapper modelMapper;

    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(ModelMapper modelMapper, TagRepository tagRepository) {
        this.modelMapper = modelMapper;
        this.tagRepository = tagRepository;
    }

    @Override
    public void create(TagDto entityDto) {
        tagRepository.create(modelMapper.map(entityDto, Tag.class));
    }



    @Override
    public void delete(long id) {
        tagRepository.delete(id);
    }

    @Override
    public void update(TagDto entityDto) {
        tagRepository.update(modelMapper.map(entityDto, Tag.class));
    }

    @Override
    public TagDto read(long id) {
        return modelMapper.map(tagRepository.read(id), TagDto.class);
    }

    @Override
    public List<TagDto> readAll() {
        return tagRepository.readAll()
                .stream()
                .map(t -> modelMapper.map(t, TagDto.class))
                .collect(Collectors.toList());
    }
}
