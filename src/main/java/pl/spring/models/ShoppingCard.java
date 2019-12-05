package pl.spring.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ShoppingCard implements Serializable {
	
	private int counter = 0;
	private List<Book> bookList = new ArrayList<>();

}
