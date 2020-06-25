package com.epam.lab.service.impl;

import com.epam.lab.dto.AuthorDto;
import com.epam.lab.model.Author;
import com.epam.lab.repository.AuthorRepository;
import com.epam.lab.service.AuthorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private ModelMapper modelMapper;

    private AuthorRepository authorRepository;


    @Autowired
    public AuthorServiceImpl(ModelMapper modelMapper, AuthorRepository authorRepository) {
        this.modelMapper = modelMapper;
        this.authorRepository = authorRepository;
    }

    public List<AuthorDto> readAll() {
        return authorRepository.readAll()
                .stream()
                .map(a -> modelMapper.map(a, AuthorDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(AuthorDto authorDto) {
        authorRepository.create(modelMapper.map(authorDto, Author.class));
    }

    @Override
    public void delete(long id) {
        authorRepository.delete(id);
    }


    @Override
    public void update(AuthorDto entityDto) {
        authorRepository.update(modelMapper.map(entityDto, Author.class));
    }

    @Override
    public AuthorDto read(long id) {
        return modelMapper.map(authorRepository.read(id), AuthorDto.class);
    }

}
