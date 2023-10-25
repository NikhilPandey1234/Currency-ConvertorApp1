package com.currency.convertor.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(InternalServerException.class)
    public ModelAndView handleInternalServerException(InternalServerException ex) {
        ModelAndView modelAndView = new ModelAndView("error/internal-server-error");
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(CurrencyNotConvertedException.class)
    public ModelAndView handleCurrencyNotConvertedException(CurrencyNotConvertedException ex) {
        ModelAndView modelAndView = new ModelAndView("error/currency-not-converted");
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }
}
