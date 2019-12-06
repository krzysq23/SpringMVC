package pl.spring.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Order {

    private String id;
    private String userLogin;
    private String payment;
    private String delivery;
    private String adress;
    private String phone;
    private String email;
    private Date dateCreated;
    private String status;
    private double amount;
    private List<OrderPosition> positions = new ArrayList<OrderPosition>();
    
}
