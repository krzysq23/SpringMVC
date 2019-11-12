package pl.spring.controller;

import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieSession;
import pl.spring.models.Book;
import pl.spring.springmvc.DroolsBeanFactory;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    KieSession kieSession = new DroolsBeanFactory().getKieSession();

    @Test
    public void testDrools() {
        Book book = new Book("Potop", "Sienkiewicz", "ISBN-123");
        kieSession.insert(book);
        kieSession.fireAllRules();
        assertEquals("ISBN-123456789", book.getIsbn());
    }

    @Test
    void addBook() {
    }

    @Test
    void removeBook() {
    }

    @Test
    void editBook() {
    }
}