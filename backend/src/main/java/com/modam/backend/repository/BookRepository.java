package com.modam.backend.repository;

import com.modam.backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {  // 기존 Integer → String 변경
}
