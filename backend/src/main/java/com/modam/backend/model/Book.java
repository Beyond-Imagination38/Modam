package com.modam.backend.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "bookId", length = 13, nullable = false) // ISBN
    private String bookId;

    @Column(name = "bookTitle", nullable = false)
    private String bookTitle;

    @Column(name = "writer", nullable = false)
    private String writer;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "publishedDate")
    private LocalDate publishedDate;

    @Column(name = "summary")
    private String summary;
}
