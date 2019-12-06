package pl.spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.spring.models.Book;
import pl.spring.service.BookService;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    BookService bookService;

    @ApiOperation(value = "Widok książek", nickname = "Widok książek")
    @GetMapping("/bookList")
    public String bookList(Model model) {
        List<Book> books = bookService.getAllBook();
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
    public String addBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes) {
    	try { 
            bookService.addBook(book);
            redirectAttributes.addFlashAttribute("info", "Dodano książkę");
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
            redirectAttributes.addFlashAttribute("info", "Nie można dodać książki");
        }
        return "redirect:/bookList";
    }

    @ApiOperation(value = "Usuwanie książki", nickname = "Usuwanie książki")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Id książki", required = true, dataType = "String", paramType = "path", defaultValue="1")
    })
    @GetMapping(value = "/removeBook/{id}")
    public String removeBook(@PathVariable String id, RedirectAttributes redirectAttributes) {
        bookService.removeBookById(id);
        return "redirect:/bookList";
    }

    @ApiOperation(value = "Metoda edutuje książkę", nickname = "Metoda edytuje książkę")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "book", value = "obiekt Book JSON", required = true, dataType = "Book", paramType = "ModelAttribute")
    })
    @GetMapping("/editBook/{id}")
    public String editBook(@PathVariable String id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "addBook";
    }

}
