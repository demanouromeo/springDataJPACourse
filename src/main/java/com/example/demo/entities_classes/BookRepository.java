package com.example.demo.entities_classes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Integer> {

  //@Query("SELECT b FROM Book b WHERE b.author = :author")
  List<Book> findByAuthor(Author author);

  //@Query("SELECT b FROM Book b WHERE b.title LIKE :keyword ESCAPE '\\'")
  List<Book> findByTitleContaining(String keyword);

  //@Query("SELECT b FROM Book b WHERE b.price BETWEEN :minPrice AND :maxPrice")
  List<Book> findByPriceBetween(double minPrice, double maxPrice);
}
