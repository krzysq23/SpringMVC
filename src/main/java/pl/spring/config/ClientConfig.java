package pl.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("pl.spring.client")
public class ClientConfig {

    public ClientConfig() {
        super();
    }

}