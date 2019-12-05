package pl.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.ApiOperation;
import pl.spring.models.Book;
import pl.spring.service.BookService;

@Controller
public class BookStoreController {

    @Autowired
    BookService bookService;

    @ApiOperation(value = "Widok księgarni")
    @GetMapping("/library")
    public String bookList(Model model) {
        List<Book> books = bookService.getAllBook();
        model.addAttribute("list", books);
        return "library";
    }

    @ApiOperation(value = "Widok książki")
    @GetMapping("/book/{id}")
    public String bookView(@PathVariable String id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "book";
    }
    
    @ApiOperation(value = "Widok koszyka")
    @GetMapping("/shoppingCard")
    public String shoppingCard(Model model) {
        List<Book> books = bookService.getAllBook();
        model.addAttribute("list", books);
        return "shoppingCard";
    }
    
    @ApiOperation(value = "Widok koszyka")
    @GetMapping("/addToCard/{bookId}")
    public @ResponseBody String addToCard(@PathVariable String bookId) {
    	System.out.println(bookId);
        return bookId;
    }
}
