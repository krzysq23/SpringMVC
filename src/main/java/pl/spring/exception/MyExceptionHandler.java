package pl.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@Component
@ControllerAdvice
public class MyExceptionHandler implements ExceptionMapper<MyApplicationException> {

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    @ResponseStatus(code= HttpStatus.BAD_REQUEST, reason="Bad Request", value=HttpStatus.BAD_REQUEST)
    public void handleBadRequest(HttpClientErrorException.BadRequest e) {
        System.out.println("Exception handler: " + e.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    @ResponseStatus(code=HttpStatus.NOT_FOUND, reason="Not Found", value=HttpStatus.NOT_FOUND)
    public void handleNotFound(HttpClientErrorException.NotFound e) {
        System.out.println("Exception handler: " + e.getMessage());
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    @ResponseStatus(code=HttpStatus.INTERNAL_SERVER_ERROR, reason="Internal Server Error", value=HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleInternalServerError(HttpServerErrorException.InternalServerError e) {
        System.out.println("Exception handler: " + e.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ModelAndView handleUnauthorizedError(HttpClientErrorException.Unauthorized e,
                                                HttpServletRequest request, HttpServletResponse response) {
        e.printStackTrace();
        ModelAndView model = new ModelAndView("login");
        model.addObject("error", "Problem z uwierzytelnieniem");
        return model;
    }


    @Override
    public Response toResponse(MyApplicationException e) {
        return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
    }
}