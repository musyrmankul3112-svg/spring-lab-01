package kz.iitu.springlab.kz.iitu.springlab.web;

import kz.iitu.springlab.catalog.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(assignableTypes = BookPageController.class)
public class BookExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ModelAndView handleBookNotFound(BookNotFoundException ex) {
        ModelAndView mav = new ModelAndView("error/404");
        mav.addObject("message", ex.getMessage());
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }
}