package pl.spring.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.swagger.annotations.ApiOperation;
import pl.spring.models.Book;
import pl.spring.models.ShoppingCard;
import pl.spring.service.BookService;

@Controller
public class BookStoreController {

    @Autowired
    BookService bookService;

    @Resource(name = "shoppingCard")
    ShoppingCard shoppingCard;
    
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
        model.addAttribute("list", shoppingCard.getBookList());
        return "shoppingCard";
    }
    
    @ApiOperation(value = "Widok zamówień")
    @GetMapping("/myOrders")
    public String myOrders(Model model) {
        model.addAttribute("list", shoppingCard.getBookList());
        return "myOrders";
    }
    
    @ApiOperation(value = "Dodawanie elementu do koszka")
    @GetMapping("/addToCard/{bookId}")
    public String addToCard(@PathVariable String bookId) {
    	Book book = shoppingCard.getBookList().stream()
    			.filter(b -> b.getId().equals(bookId))
    			.findAny()
    			.orElse(null);
    	if(book != null) {
    		book.setQty(book.getQty()+ 1);
    	} else {
    		book = bookService.getBookById(bookId);
    		book.setQty(1);
    		shoppingCard.getBookList().add(book);
    		shoppingCard.setCounter(shoppingCard.getCounter() + 1);
    	}
        return "redirect:/shoppingCard";
    }
    
    @ApiOperation(value = "Usuwanie elementu z koszyka")
    @GetMapping("/removeFromCard/{bookId}")
    public @ResponseBody String removeFromCard(@PathVariable String bookId) {
    	shoppingCard.getBookList()
    		.removeIf(b -> b.getId().equals(bookId));
    	shoppingCard.setCounter(shoppingCard.getCounter() -1);
        return bookId;
    }
    
    
    @ApiOperation(value = "Zamawianie produktów")
    @GetMapping("/productsOrder")
    public String productsOrder(Model model) {
    	if(shoppingCard.getBookList().size() > 0) {
    		double price = shoppingCard.getBookList()
    			.stream()
    			.mapToDouble(b -> (b.getQty() * b.getPrice()))
    			.sum();
    		shoppingCard.setPrice(price);
            model.addAttribute("list", shoppingCard.getBookList());
            return "productsOrder";
    	} else {
    		return "redirect:/shoppingCard";
    	}
    }
    
    @ApiOperation(value = "Wysyłanie zamówienia")
    @PostMapping("/createProductsOrder")
    public String createProductsOrder(HttpServletRequest request, RedirectAttributes redirectAttributes) {
    	
		String adress = request.getParameter("deliveryAdres"), phone = request.getParameter("phone"), 
				email = request.getParameter("email"), payment = request.getParameter("payment"),
						delivery = request.getParameter("delivery");
        
        redirectAttributes.addFlashAttribute("info", "Zamówienie zostało wysłane!");
        shoppingCard.cleanCard();
        return "redirect:/myOrders";
    }
}
