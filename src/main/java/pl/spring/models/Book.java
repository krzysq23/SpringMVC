package pl.spring.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
public class Book implements Serializable {

    private String id;
    private String title;
    private String author;
    private String isbn;
    private String imageSrc;
    private Publisher publisher = new Publisher();

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }
}

@Getter
@Setter
class Publisher implements Serializable {
    private String id;
    private String name;
    private int founded;
    private String location;
}
