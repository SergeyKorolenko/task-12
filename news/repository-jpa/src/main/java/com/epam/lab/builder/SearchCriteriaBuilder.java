package com.epam.lab.builder;

import com.epam.lab.model.*;

import javax.persistence.criteria.*;
import java.util.*;

import static com.epam.lab.model.AbstractEntity_.ID;

public class SearchCriteriaBuilder {

    private SearchCriteria searchCriteria;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<News> criteriaQuery;
    private Root<News> newsRoot;
    private Join<Author, News> authorNewsJoin;
    private Join<Tag, News> tagNewsJoin;
    private List<Predicate> predicates = new ArrayList<>();

    public SearchCriteriaBuilder(SearchCriteria searchCriteria, CriteriaBuilder criteriaBuilder) {
        this.searchCriteria = searchCriteria;
        this.criteriaBuilder = criteriaBuilder;
        this.criteriaQuery = criteriaBuilder.createQuery(News.class);
        this.newsRoot = criteriaQuery.from(News.class);
        this.authorNewsJoin = newsRoot.join(News_.AUTHOR);
        this.tagNewsJoin = newsRoot.join(News_.TAG_SET, JoinType.LEFT);
    }

    private void addAuthorName() {
        if (isNotNullAndNotEmpty(searchCriteria.getName())) {
            predicates.add(criteriaBuilder.equal(authorNewsJoin.get(Author_.NAME), searchCriteria.getName()));
        }
    }

    private void addAuthorSurname() {
        if (isNotNullAndNotEmpty(searchCriteria.getSurname())) {
            predicates.add(criteriaBuilder.equal(authorNewsJoin.get(Author_.SURNAME), searchCriteria.getSurname()));
        }
    }

    private void addTagList() {
        if (isNotEmpty(searchCriteria.getTags())) {
            predicates.add(tagNewsJoin.get(Tag_.NAME).in(searchCriteria.getTags()));
        }
    }

    private void addGroupBy() {
        criteriaQuery.where(predicates.toArray(new Predicate[0])).
                groupBy(newsRoot.get(ID), authorNewsJoin.get(Author_.NAME), newsRoot.get(News_.AUTHOR).get(ID),
                        authorNewsJoin.get(Author_.SURNAME));
    }

    private void addHaving() {
        if (isNotEmpty(searchCriteria.getTags())) {
            criteriaQuery.having(criteriaBuilder.equal(criteriaBuilder.count(tagNewsJoin.get(ID)),
                    searchCriteria.getTags().size()));
        }
    }

    private void addOrderBy() {
        if (isNotEmpty(searchCriteria.getSortBy())) {
            Set<Order> orders = new HashSet<>();
            searchCriteria.getSortBy().forEach(s -> {
                try {
                    OrderByParameter parameter = OrderByParameter.valueOf(s.toUpperCase());
                    switch (parameter) {
                        case DATE:
                            orders.add(criteriaBuilder.asc(newsRoot.get(News_.CREATION_DATE)));
                            break;
                        case NAME:
                            orders.add(criteriaBuilder.asc(authorNewsJoin.get(Author_.NAME)));
                            break;
                        case SURNAME:
                            orders.add(criteriaBuilder.asc(authorNewsJoin.get(Author_.SURNAME)));
                    }
                } catch (IllegalArgumentException ignored) {
                }

            });
            criteriaQuery.orderBy(orders.toArray(new Order[0]));
        }
    }

    public CriteriaQuery<News> build() {
        this.addAuthorName();
        this.addAuthorSurname();
        this.addTagList();
        this.addGroupBy();
        this.addHaving();
        this.addOrderBy();
        return criteriaQuery;
    }

    private boolean isNotEmpty(Set<String> stringSet) {
        return !stringSet.isEmpty();
    }

    private boolean isNotNullAndNotEmpty(String criteriaParameter) {
        return criteriaParameter != null && !criteriaParameter.isEmpty();
    }
}
