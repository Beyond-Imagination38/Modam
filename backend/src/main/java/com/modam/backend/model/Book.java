package com.modam.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false)
    private int bookId;

    @Column(name = "book_title", nullable = false)
    private String book_title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "published_date")
    private LocalDate published_date;

    @Column(name = "cover_image")
    private String coverImage;

    // getTitle
    public String getTitle() { return this.book_title; }  // getter 삽입
    // coverImage
    public String getCoverImage(){
        return coverImage;
    }

}