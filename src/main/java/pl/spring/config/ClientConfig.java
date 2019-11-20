package pl.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.spring.client.RestTemplateFactory;

@Configuration
@ComponentScan("pl.spring.client")
public class ClientConfig {

    public ClientConfig() {
        super();
    }

}