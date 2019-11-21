package pl.spring.client;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpHost;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Getter
@Setter
@Component
public class RestTemplateFactory implements FactoryBean<RestTemplate>, InitializingBean {

    private RestTemplate restTemplate;
    private String login = "";
    private String password = "";

    @Value("${rest.hostName}")
    private String hostName;

    @Value("${rest.port}")
    private int port;

    public RestTemplateFactory() {
        super();
    }

    @Override
    public RestTemplate getObject() {
        return restTemplate;
    }

    @Override
    public Class<RestTemplate> getObjectType() {
        return RestTemplate.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() {
        HttpHost host = new HttpHost(hostName, port, "http");
        restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactoryBasicAuth(host));
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(login, password));
        /*restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());*/
    }

}