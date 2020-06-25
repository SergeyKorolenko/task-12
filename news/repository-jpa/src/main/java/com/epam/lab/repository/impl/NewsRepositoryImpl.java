package com.epam.lab.repository.impl;

import com.epam.lab.builder.SearchCriteriaBuilder;
import com.epam.lab.exception.DataNotFoundException;
import com.epam.lab.exception.DuplicateDataException;
import com.epam.lab.model.*;
import com.epam.lab.repository.NewsRepository;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;

import static com.epam.lab.model.AbstractEntity_.ID;

@Repository
public class NewsRepositoryImpl implements NewsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public long create(News entity) {
        entity.setCreationDate(LocalDate.now());
        entity.setModificationDate(LocalDate.now());
        Session session = entityManager.unwrap(Session.class);
        try {
            session.replicate(entity, ReplicationMode.IGNORE);
        } catch (ConstraintViolationException e) {
            throw new DuplicateDataException();
        }
        return entity.getId();
    }

    @Override
    @Transactional
    public void update(News entity) {
        entity.setCreationDate(LocalDate.now());
        entity.setModificationDate(LocalDate.now());
        Session session = entityManager.unwrap(Session.class);
        session.replicate(entity.getAuthor(), ReplicationMode.IGNORE);
        try {
            entity.getTagSet().forEach(tag -> session.replicate(tag, ReplicationMode.IGNORE));
        } catch (ConstraintViolationException e) {
            throw new DuplicateDataException();
        }
        session.merge(entity);
    }

    @Override
    @Transactional
    public void delete(long id) {
        entityManager.remove(read(id));
    }

    @Override
    public News read(long id) {
        News news = entityManager.find(News.class, id);
        if (news != null) {
            return news;
        } else {
            throw new DataNotFoundException();
        }
    }

    @Override
    public List<News> readAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> criteriaQuery = criteriaBuilder.createQuery(News.class);
        Root<News> newsRoot = criteriaQuery.from(News.class);
        criteriaQuery.select(newsRoot);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Long getCount() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<News> newsRoot = criteriaQuery.from(News.class);
        criteriaQuery.select(criteriaBuilder.count(newsRoot.get(ID)));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<News> searchByCriteria(SearchCriteria searchCriteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        SearchCriteriaBuilder searchCriteriaBuilder = new SearchCriteriaBuilder(searchCriteria,
                criteriaBuilder);
        CriteriaQuery<News> criteriaQuery = searchCriteriaBuilder.build();
        int start = searchCriteria.getPage() * searchCriteria.getSize() - searchCriteria.getSize();
        searchCriteria.setCount(entityManager.createQuery(criteriaQuery).getResultList().size());
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(start)
                .setMaxResults(searchCriteria.getSize())
                .getResultList();
    }
}
