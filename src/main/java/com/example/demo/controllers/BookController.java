package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities_classes.Book;
import com.example.demo.entities_classes.BookRepository;

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/book")
    public Book createBook(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        return savedBook;
    }

    // Get all books
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get book by ID
    @GetMapping("books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id.intValue());
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update an existing book
    /*
    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Optional<Book> optionalBook = bookRepository.findById(id.intValue());
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(bookDetails.getTitle()); 
            book.setPrice(bookDetails.getPrice());
            book.setImageUrl(bookDetails.getImageUrl());
            book.setAuthor(bookDetails.getAuthor()); 
            Book updatedBook = bookRepository.save(book);
            return updatedBook;
        } else {
            return new Book(); // Return an empty book or handle as needed
        }
    }
     */
    /**
     * This method implements the real put philosophy. Update if id is found, otherwise create a new book with the provided id.
     * That is why it always returns a book, either updated or newly created. The id is set to the provided id, so if it is a new book, it will be created with that id.
     */
    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        bookDetails.setId(id);
        bookRepository.save(bookDetails);
        return bookDetails;
    }

    @PutMapping("/books/2/{id}")
    public ResponseEntity<Void> updateBook2(@PathVariable Long id, @RequestBody Book bookDetails) {
        Optional<Book> optionalBook = bookRepository.findById(id.intValue());
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(bookDetails.getTitle());
            book.setPrice(bookDetails.getPrice());
            book.setImageUrl(bookDetails.getImageUrl());
            book.setAuthor(bookDetails.getAuthor());
            Book updatedBook = bookRepository.save(book);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a book
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id.intValue());
        if (book.isPresent()) {
            bookRepository.delete(book.get());
            //return ResponseEntity.noContent().build();
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /* 
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Optional<Book> optionalBook = bookRepository.findById(id.intValue());
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(bookDetails.getTitle()); 
            book.setPrice(bookDetails.getPrice());
            book.setImageUrl(bookDetails.getImageUrl());
            book.setAuthor(bookDetails.getAuthor()); 
            Book updatedBook = bookRepository.save(book);
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
     */

 /* 
    // Delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id.intValue());
        if (book.isPresent()) {
            bookRepository.delete(book.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Search books by title containing keyword
    @GetMapping("/search")
    public List<Book> searchBooksByTitle(@RequestParam String keyword) {
        return bookRepository.findByTitleContaining(keyword);
    }

    // Find books by author (assuming author is a string, but actually it's an Author entity)
    // This might need adjustment based on how author is handled
    @GetMapping("/author/{authorName}")
    public List<Book> getBooksByAuthor(@PathVariable String authorName) {
        return bookRepository.findByAuthor(authorName);
    }

    // Find books by price range
    @GetMapping("/price")
    public List<Book> getBooksByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
        return bookRepository.findByPriceBetween(minPrice, maxPrice);
    }

    // Create a new book     
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }
     */
}
