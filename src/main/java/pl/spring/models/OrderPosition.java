package pl.spring.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderPosition {
	
    private String id;
    private int quantity;
    private Book book;
    
}
