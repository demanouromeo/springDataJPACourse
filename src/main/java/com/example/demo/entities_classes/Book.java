package com.example.demo.entities_classes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String isbn;
    private int publicationYear;
    private double price;
    private String imageUrl;

    @ManyToOne
    private Author author;

    @ManyToOne
    private Publisher publisher;

    @ManyToOne
    private Category category;

    // Constructors
    public Book() {}

    public Book(String title, String isbn, int publicationYear, double price, Author author, String imageUrl, Publisher publisher, Category category) {
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.price = price;
        this.imageUrl = imageUrl;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getPublicationYear() { return publicationYear; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }

    public Publisher getPublisher() { return publisher; }
    public void setPublisher(Publisher publisher) { this.publisher = publisher; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publicationYear=" + publicationYear +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", author=" + (author != null ? author.getId() : null) +
                ", publisher=" + (publisher != null ? publisher.getId() : null) +
                ", category=" + (category != null ? category.getId() : null) +
                '}';
    }
}

