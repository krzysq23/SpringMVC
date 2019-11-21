package pl.spring.service;

import org.springframework.http.HttpEntity;

public interface HttpService {

    HttpEntity<String> getEntity(String json);
}
