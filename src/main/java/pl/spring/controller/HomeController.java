package pl.spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.spring.client.RestTemplateFactory;
import pl.spring.models.Book;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class HomeController {

    @Value("${rest.url}")
    private String uri;

    @Autowired
    RestTemplate restTemplate;

    @Resource(name = "&restTemplateFactory")
    private RestTemplateFactory restTemplateFactory;

    @ApiOperation(value = "Widok logowania", nickname = "Widok logowania")
    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @ApiOperation(value = "Metoda logowania", nickname = "Metoda logowania")
    @PostMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        String login = request.getParameter("login"), password = request.getParameter("password"), navigateURL = "redirect:/home";
        restTemplateFactory.setLogin(login);
        restTemplateFactory.setPassword(password);
        restTemplateFactory.afterPropertiesSet();
        restTemplate = restTemplateFactory.getObject();
        ResponseEntity<Object> response = restTemplate.exchange(uri + "async",
                HttpMethod.GET, null, Object.class);
        if(!response.getStatusCode().equals(HttpStatus.OK)) {
            navigateURL = "login";
            model.addAttribute("error" , "Nieporawny login lub hasło!");
        }
        return navigateURL;
    }

    @ApiOperation(value = "Widok tworzenia konta", nickname = "Widok tworzenia konta")
    @GetMapping("/logout")
    public String logout(Model model) {
        restTemplateFactory.setLogin("");
        restTemplateFactory.setPassword("");
        restTemplateFactory.afterPropertiesSet();
        restTemplate = restTemplateFactory.getObject();
        model.addAttribute("error" , "Zostałeś wylogowany!");
        return "login";
    }

    @ApiOperation(value = "Widok tworzenia konta", nickname = "Widok tworzenia konta")
    @GetMapping("/createAccount")
    public String createAccountPage(Model model) {
        return "createAccount";
    }

    @ApiOperation(value = "Widok główny", nickname = "Widok główny")
    @GetMapping("/home")
    public String homePage(Model model) throws HttpClientErrorException {

        ResponseEntity<List<Book>> bookResponse = restTemplate.exchange(uri+"getAll",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Book>>() {});
        List<Book> books = bookResponse.getBody();
/*        Object[] forNow = restTemplate.getForObject(uri+"getAll", Object[].class);
        List<Object> searchList = Arrays.asList(forNow);*/
        model.addAttribute("list", books);
        return "home";
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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        HttpEntity<String> entity = new HttpEntity<String>(json, headers);
        ResponseEntity<String> loginResponse = restTemplate.exchange(uri+"addBook", HttpMethod.POST, entity, String.class);
        if (loginResponse.getStatusCode() == HttpStatus.OK) {
            redirectAttributes.addAttribute("info", "Dodano książkę");
        } else {
            redirectAttributes.addAttribute("info", "Nie można dodać książki");
        }
        return "redirect:/home";
    }

    @ApiOperation(value = "Usuwanie książki", nickname = "Usuwanie książki")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Id książki", required = true, dataType = "String", paramType = "path", defaultValue="1")
    })
    @GetMapping(value = "/removeBook/{id}")
    public String removeBook(@PathVariable String id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("info", "Usunięto książkę");
        restTemplate.delete(uri + "removeBook/" + id);
        return "redirect:/home";
    }

    @ApiOperation(value = "Metoda edutuje książkę", nickname = "Metoda edytuje książkę")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "book", value = "obiekt Book JSON", required = true, dataType = "Book", paramType = "ModelAttribute")
    })
    @GetMapping("/editBook/{id}")
    public String editBook(@PathVariable String id, Model model) {
        ResponseEntity<Book> bookResponse = restTemplate.exchange(uri+"findByIsbn-"+id,
                HttpMethod.GET, null, new ParameterizedTypeReference<Book>() {});
        Book book = bookResponse.getBody();
        model.addAttribute("book", book);
        return "addBook";
    }

    @ModelAttribute("title")
    public String fetTitle(){
        return "SpringMVC";
    }
}
