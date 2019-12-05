package pl.spring.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.beans.Transient;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
public class Book implements Serializable {

	private static final long serialVersionUID = -4960570387584394225L;
	
	private String id;
    private String title;
    private String author;
    private String isbn;
    private String imageSrc;
    private double price;
    private Publisher publisher = new Publisher();

    private transient Integer qty;
    
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }
}

@Getter
@Setter
class Publisher implements Serializable {

	private static final long serialVersionUID = -5435530832320436459L;
	
	private String id;
    private String name;
    private int founded;
    private String location;
}
