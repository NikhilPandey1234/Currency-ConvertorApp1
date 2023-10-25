package com.currency.convertor.controller;

import com.currency.convertor.dto.CurrencyRateDto;
import com.currency.convertor.exceptions.InternalServerException;
import com.currency.convertor.services.CurrencyRateService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
@RequestMapping("")
public class CurrencyController {

    @Autowired
    private CurrencyRateService currencyRateService;

    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    @GetMapping("/k")
    public String home() {
        logger.info("Accessed the home page.");
        return "index";
    }

    //@GetMapping("/currency-conversion")
    @PostMapping("/currency-conversion")
    public String convertCurrency(
            CurrencyRateDto currencyRateDto,
            RedirectAttributes redirectAttributes
    ) {
        try {
            logger.info("Received currency conversion request with parameters: " + currencyRateDto);
            currencyRateService.saveCurrency(currencyRateDto);
            logger.info("Currency conversion successful.");
            redirectAttributes.addFlashAttribute("successMessage", "Currency conversion successful!");
        } catch (Exception e) {
            logger.error("Currency conversion failed: " + e.getMessage(), e);
            //throw new InternalServerException("Internal Server Error: " + e.getMessage());
            return "currencyconversionFailed";
        }
        return "redirect:/k";
    }

    @GetMapping("/currency-conversion-form")
    public String currencyConversionForm(Model model) {
        logger.info("Accessed currency conversion form.");
        model.addAttribute("currencyRateDto", new CurrencyRateDto());
        return "index";
    }

    @GetMapping("/conversion-history")
    public String conversionHistory(Model model) {
        // Get conversion history from the service
        try {
            logger.info("Accessed conversion history.");
            model.addAttribute("conversionHistory", currencyRateService.getAllData());
            return "history";
        } catch (Exception e) {
            logger.error("Failed to retrieve conversion history: " + e.getMessage(), e);
            throw new InternalServerException("Internal Server Error: " + e.getMessage());
        }
    }

    @PostMapping("/changeLang")
    public String changeLang(@RequestParam String lang, HttpServletRequest request, HttpServletResponse response) {

        System.out.println( "Change lang " + lang);
        request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale(lang));



        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("pageTitle", "Currency Converter Application");

        Locale currentLocale = request.getLocale();
        String countryCode = currentLocale.getCountry();
        String countryName = currentLocale.getDisplayCountry();

        String langCode = currentLocale.getLanguage();
        String langName = currentLocale.getDisplayLanguage();

        System.out.println(countryCode + ": " + countryName);
        System.out.println(langCode + ": " + langName);

        System.out.println("//////");
        String[] language = Locale.getISOLanguages();

        for (String lang : language) {
            Locale locale = new Locale(lang);
//            System.out.println(lang +":"+locale.getDisplayLanguage());
        }

        return "index";
    }
}
