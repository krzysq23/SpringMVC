package pl.spring.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@ComponentScan(basePackages = "pl.spring.controller" )
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ApplicationContext context;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        boolean status = true;
        RestTemplateFactory restTemplateFactory = (RestTemplateFactory) context.getBean("&restTemplateFactory");
        if(!restTemplateFactory.isAuthorized()) {
            ModelAndView mav = new ModelAndView("login");
            mav.addObject("error", "Zostałeś wylogowany!");
            throw new ModelAndViewDefiningException(mav);
        } else {
            return true;
        }
    }

}