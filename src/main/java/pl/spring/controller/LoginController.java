package pl.spring.controller;

import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import pl.spring.client.RestTemplateFactory;
import pl.spring.models.User;
import pl.spring.service.HttpService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class LoginController {

    @Value("${rest.url}")
    private String uri;

    @Autowired
    HttpService httpService;

    @Resource(name = "&restTemplateFactory")
    private RestTemplateFactory restTemplateFactory;

    @ApiOperation(value = "Widok logowania", nickname = "Widok logowania")
    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "login";
    }

    @ApiOperation(value = "Metoda logowania", nickname = "Metoda logowania")
    @PostMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        String login = request.getParameter("login"), password = request.getParameter("password"), navigateURL = "redirect:/home";
        restTemplateFactory.setLogin(login);
        restTemplateFactory.setPassword(password);
        restTemplateFactory.afterPropertiesSet();
        ResponseEntity<Object> response = restTemplateFactory.getObject().exchange(uri + "async",
                HttpMethod.GET, null, Object.class);
        if(response.getStatusCode().value() != 200) {
            navigateURL = "login";
            model.addAttribute("error" , "Nieporawny login lub hasło!");
            restTemplateFactory.setAuthorized(false);
        } else {
            restTemplateFactory.setAuthorized(true);
        }
        return navigateURL;
    }

    @ApiOperation(value = "Wylgowanie", nickname = "Wylogowanie")
    @GetMapping("/logout")
    public String logout(Model model) {
        restTemplateFactory.setLogin("");
        restTemplateFactory.setPassword("");
        restTemplateFactory.setAuthorized(false);
        restTemplateFactory.afterPropertiesSet();
        model.addAttribute("error" , "Zostałeś wylogowany!");
        return "login";
    }

    @ApiOperation(value = "Widok tworzenia konta", nickname = "Widok tworzenia konta")
    @GetMapping("/createAccount")
    public String createAccountPage(Model model) {
        return "createAccount";
    }

    @ApiOperation(value = "Metoda tworzy konto", nickname = "Metoda tworzy konto")
    @PostMapping("/createAccount")
    public String createAccount( HttpServletRequest request, Model model ) {
        String  navigateURL = "login";
        String json = new JSONObject()
                .put("login", request.getParameter("login"))
                .put("password", request.getParameter("password"))
                .put("firstName", request.getParameter("firstName"))
                .put("surname", request.getParameter("surname"))
                .put("email", request.getParameter("email"))
                .toString();

        ResponseEntity<String> loginResponse = restTemplateFactory.getObject().exchange(uri + "createUser",
                HttpMethod.POST, httpService.getEntity(json), String.class);
        if (loginResponse.getStatusCode() == HttpStatus.OK) {
            model.addAttribute("error" , "Konto zostało utworzone!");
        } else {
            navigateURL = "createAccount";
            model.addAttribute("error" , "Nie udało się stworzyć konta!");
        }
        return navigateURL;
    }

    @ApiOperation(value = "Widok użytkowników", nickname = "Widok użytkoników")
    @GetMapping("/userList")
    public String bookList(Model model) throws HttpClientErrorException {
        ResponseEntity<List<User>> userResponse = restTemplateFactory.getObject().exchange(uri + "getAllUsers",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});
        List<User> users = userResponse.getBody();
        model.addAttribute("userList", users);
        return "userList";
    }
}
