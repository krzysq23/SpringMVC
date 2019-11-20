package pl.spring.client;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.spring.exception.MyApplicationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {

        return (
                httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {

        if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR) {


        } else if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR) {

        };

        throw HttpServerErrorException.create(httpResponse.getStatusCode(), httpResponse.getStatusText(), httpResponse.getHeaders(), null, null);
    }

}