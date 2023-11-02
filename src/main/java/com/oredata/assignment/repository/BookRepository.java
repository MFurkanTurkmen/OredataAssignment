package com.oredata.assignment.repository;

import com.oredata.assignment.repository.entity.Book;
import com.oredata.assignment.repository.entity.Order_Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findOptionalByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
    @Query( value = "SELECT b " +
            "FROM Book b " +
            "INNER JOIN Order_Book ob ON ob.bookId = b.id " +
            "INNER JOIN Order o ON o.id = ob.orderId " +
            "WHERE o.id = ?1",nativeQuery = false)
    List<Book> findBookForOrderId2(Long id);


}
