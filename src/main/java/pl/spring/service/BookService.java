package pl.spring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import pl.spring.models.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBook();
    Book getBookById(String id);
    boolean addBook(Book book) throws JsonProcessingException;
    void removeBookById(String id);

}
