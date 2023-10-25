package com.currency.convertor.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;


import java.util.Locale;

public class LocaleChangeInterceptor implements HandlerInterceptor {

    private final LocaleResolver localeResolver;

    public LocaleChangeInterceptor(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String lang = request.getParameter("lang");
        if (lang != null) {
            Locale newLocale = new Locale(lang);
            localeResolver.setLocale(request, response, newLocale);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Do nothing here
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Do nothing here
    }

}
