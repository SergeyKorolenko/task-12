package com.epam.lab.repository.impl;

import com.epam.lab.exception.DataNotFoundException;
import com.epam.lab.exception.DuplicateDataException;
import com.epam.lab.model.Author;
import com.epam.lab.repository.AuthorRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public long create(Author entity) {
        try {
            entityManager.persist(entity);
        } catch (PersistenceException ex) {
            throw new DuplicateDataException();
        }
        return entity.getId();
    }

    @Override
    @Transactional
    public void update(Author entity) {
        entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(long id) {
        entityManager.remove(read(id));
    }

    @Override
    public Author read(long id) {
        Author author = entityManager.find(Author.class, id);
        if (author != null) {
            return author;
        } else {
            throw new DataNotFoundException();
        }
    }

    @Override
    public List<Author> readAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);
        Root<Author> author = criteriaQuery.from(Author.class);
        criteriaQuery.select(author);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
