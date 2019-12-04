package pl.spring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.spring.client.RestTemplateFactory;
import pl.spring.models.Book;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookServiceImp implements BookService {

    @Value("${rest.url}")
    private String uri;

    @Autowired
    HttpService httpService;

    @Resource(name = "&restTemplateFactory")
    private RestTemplateFactory restTemplateFactory;

    @Override
    public List<Book> getAllBook() {
        ResponseEntity<List<Book>> bookResponse = restTemplateFactory.getObject().exchange(uri+"getAll",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Book>>() {});
        /*        Object[] forNow = restTemplate.getForObject(uri+"getAll", Object[].class);
        List<Object> searchList = Arrays.asList(forNow);*/
        return bookResponse.getBody();
    }

    @Override
    public Book getBookById(String id) {
        ResponseEntity<Book> bookResponse = restTemplateFactory.getObject().exchange(uri + "findBookById-" + id,
                HttpMethod.GET, null, new ParameterizedTypeReference<Book>() {});
        return bookResponse.getBody();
    }

    @Override
    public boolean addBook(Book book) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(book);
        ResponseEntity<String> loginResponse = restTemplateFactory.getObject().exchange(uri + "addBook",
                HttpMethod.POST, httpService.getEntity(json), String.class);
        if (loginResponse.getStatusCode() == HttpStatus.OK) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void removeBookById(String id) {
        restTemplateFactory.getObject().delete(uri + "removeBook/" + id);
    }

}
