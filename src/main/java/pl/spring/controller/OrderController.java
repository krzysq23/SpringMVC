package pl.spring.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.swagger.annotations.ApiOperation;
import pl.spring.models.AppUser;
import pl.spring.models.BookInfo;
import pl.spring.models.ShoppingCard;
import pl.spring.service.BookService;
import pl.spring.service.OrderService;

@Controller
public class OrderController {

    @Autowired
    BookService bookService;

    @Autowired
    OrderService orderService;
    
    @Resource(name = "shoppingCard")
    ShoppingCard shoppingCard;
    
    @Resource(name = "appUser")
    AppUser appUser;
    
    @ApiOperation(value = "Widok zamówień")
    @GetMapping("/myOrders")
    public String myOrders(Model model) {
        model.addAttribute("list", orderService.getOrderByUser(appUser.getLogin()));
        return "myOrders";
    }
    
    @ApiOperation(value = "Widok szczegółów zamówienia")
    @GetMapping("/orderDetails/{orderId}")
    public String orderDetails(@PathVariable String orderId, Model model) {
        model.addAttribute("order", orderService.getOrderById(orderId));
        return "orderDetails";
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
		
		List<BookInfo> booksIds = shoppingCard.getBookList()
    			.stream()
    			.map(b -> new BookInfo(b.getId(), b.getQty()))
    			.collect(Collectors.toList());
		
		JSONObject json = new JSONObject();
		json.put("adress", adress)
			.put("phone", phone).put("email", email)
			.put("payment", payment).put("delivery", delivery)
			.put("amount", shoppingCard.getPrice())
			.put("login", appUser.getLogin())
			.put("books", booksIds);
		
		boolean status = orderService.saveOrder(json.toString());
		if(status) {
			redirectAttributes.addFlashAttribute("info", "Zamówienie zostało wysłane!");
			shoppingCard.cleanCard();
			return "redirect:/myOrders";
		} else {
			redirectAttributes.addFlashAttribute("info", "Wystąpił problem podczas wysyłki!");
			return "redirect:/shoppingCard";
		}
        
    }
}
