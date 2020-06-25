package com.epam.lab.repository.impl;

import com.epam.lab.exception.DataNotFoundException;
import com.epam.lab.exception.DuplicateDataException;
import com.epam.lab.model.Tag;
import com.epam.lab.model.Tag_;
import com.epam.lab.repository.TagRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.*;
import java.util.List;

import static com.epam.lab.model.AbstractEntity_.ID;

@Repository
public class TagRepositoryImpl implements TagRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public long create(Tag entity) {
        try {
            entityManager.persist(entity);
        } catch (PersistenceException ex) {
            throw new DuplicateDataException();
        }
        return entity.getId();
    }

    @Override
    @Transactional
    public void update(Tag entity) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Tag> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Tag.class);
        Root<Tag> tagRoot = criteriaUpdate.from(Tag.class);
        criteriaUpdate.set(Tag_.NAME, entity.getName());
        criteriaUpdate.where(criteriaBuilder.equal(tagRoot.get(ID), entity.getId()));
        try {
            entityManager.createQuery(criteriaUpdate).executeUpdate();
        } catch (RuntimeException ex) {
            throw new DuplicateDataException();
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        entityManager.remove(read(id));
    }

    @Override
    public Tag read(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag != null) {
            return tag;
        } else {
            throw new DataNotFoundException();
        }
    }

    @Override
    public List<Tag> readAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tag = criteriaQuery.from(Tag.class);
        criteriaQuery.select(tag);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
