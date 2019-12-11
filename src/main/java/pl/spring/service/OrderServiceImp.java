package pl.spring.service;

import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import pl.spring.client.RestTemplateFactory;
import pl.spring.models.Order;

@Service
public class OrderServiceImp implements OrderService {

	@Value("${rest.url}")
    private String uri;

    @Autowired
    HttpService httpService;

    @Resource(name = "&restTemplateFactory")
    private RestTemplateFactory restTemplateFactory;
    
	@Override
	public boolean saveOrder(String json) {
        ResponseEntity<String> loginResponse = restTemplateFactory.getObject().exchange(uri + "saveOrder",
                HttpMethod.POST, httpService.getEntity(json), String.class);
        if (loginResponse.getStatusCode() == HttpStatus.OK) {
            return true;
        } else {
            return false;
        }
	}

	@Override
	public List<Order> getOrderByUser(String login) {
		ResponseEntity<List<Order>> bookResponse = restTemplateFactory.getObject().exchange(uri + "getAllOrdersByUser/" + login,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Order>>() {});
        return bookResponse.getBody();
	}

	@Override
	public Order getOrderById(String orderId) {
		ResponseEntity<Order> bookResponse = restTemplateFactory.getObject().exchange(uri + "findOrderById/" + orderId,
                HttpMethod.GET, null, new ParameterizedTypeReference<Order>() {});
        return bookResponse.getBody();
	}

	@Override
	public boolean payForOrder(String orderId) {
		JSONObject json = new JSONObject();
		json.put("orderId", orderId);
		ResponseEntity<String> loginResponse = restTemplateFactory.getObject().exchange(uri + "payForOrder",
                HttpMethod.POST, httpService.getEntity(json.toString()), String.class);
        if (loginResponse.getStatusCode() == HttpStatus.OK) {
            return true;
        } else {
            return false;
        }
	}
	

}
