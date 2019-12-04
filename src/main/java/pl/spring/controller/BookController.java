package pl.spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.spring.client.RestTemplateFactory;
import pl.spring.models.Book;
import pl.spring.service.HttpService;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class BookController {

    @Value("${rest.url}")
    private String uri;

    @Autowired
    HttpService httpService;

    @Resource(name = "&restTemplateFactory")
    private RestTemplateFactory restTemplateFactory;

    @ApiOperation(value = "Widok książek", nickname = "Widok książek")
    @GetMapping("/bookList")
    public String bookList(Model model) throws HttpClientErrorException {

        ResponseEntity<List<Book>> bookResponse = restTemplateFactory.getObject().exchange(uri+"getAll",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Book>>() {});
        List<Book> books = bookResponse.getBody();
/*        Object[] forNow = restTemplate.getForObject(uri+"getAll", Object[].class);
        List<Object> searchList = Arrays.asList(forNow);*/
        model.addAttribute("list", books);
        return "bookList";
    }

    @ApiOperation(value = "Widok dodawania książki", nickname = "Widok dodawania książki")
    @GetMapping("/addBook")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        return "addBook";
    }

    @ApiOperation(value = "Metoda dodaje książkę", nickname = "Metoda dodaje książkę")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "book", value = "obiekt Book JSON", required = true, dataType = "Book", paramType = "ModelAttribute")
    })
    @PostMapping("/addBook")
    public String addBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(book);

        ResponseEntity<String> loginResponse = restTemplateFactory.getObject().exchange(uri+"addBook",
                HttpMethod.POST, httpService.getEntity(json), String.class);
        if (loginResponse.getStatusCode() == HttpStatus.OK) {
            redirectAttributes.addAttribute("info", "Dodano książkę");
        } else {
            redirectAttributes.addAttribute("info", "Nie można dodać książki");
        }
        return "redirect:/bookList";
    }

    @ApiOperation(value = "Usuwanie książki", nickname = "Usuwanie książki")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Id książki", required = true, dataType = "String", paramType = "path", defaultValue="1")
    })
    @GetMapping(value = "/removeBook/{id}")
    public String removeBook(@PathVariable String id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("info", "Usunięto książkę");
        restTemplateFactory.getObject().delete(uri + "removeBook/" + id);
        return "redirect:/bookList";
    }

    @ApiOperation(value = "Metoda edutuje książkę", nickname = "Metoda edytuje książkę")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "book", value = "obiekt Book JSON", required = true, dataType = "Book", paramType = "ModelAttribute")
    })
    @GetMapping("/editBook/{id}")
    public String editBook(@PathVariable String id, Model model) {
        ResponseEntity<Book> bookResponse = restTemplateFactory.getObject().exchange(uri+"findByIsbn-"+id,
                HttpMethod.GET, null, new ParameterizedTypeReference<Book>() {});
        Book book = bookResponse.getBody();
        model.addAttribute("book", book);
        return "addBook";
    }

}
