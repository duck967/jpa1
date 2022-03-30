package com.duck.jpa1.repositories;

import java.util.Set;

import com.duck.jpa1.domain.Book;

import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
    Set<Book> findByTitle(String title);
}
