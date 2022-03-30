package com.duck.jpa1.repositories;

import java.util.Set;

import com.duck.jpa1.domain.Author;
import com.duck.jpa1.domain.Book;

import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    Set<Author> findAllByBooksContains(Book b);
}
