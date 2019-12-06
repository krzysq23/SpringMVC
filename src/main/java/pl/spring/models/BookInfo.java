package pl.spring.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookInfo implements Serializable {
	
	private String id;
    private int qty;
    
}

