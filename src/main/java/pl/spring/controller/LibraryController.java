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
import pl.spring.service.BookService;
import pl.spring.service.HttpService;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class LibraryController {

    @Autowired
    BookService bookService;

    @ApiOperation(value = "Widok księgarni")
    @GetMapping("/library")
    public String bookList(Model model) {
        List<Book> books = bookService.getAllBook();
        model.addAttribute("list", books);
        return "library";
    }

}
