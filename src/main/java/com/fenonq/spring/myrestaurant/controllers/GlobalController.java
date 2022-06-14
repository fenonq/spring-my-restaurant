package com.fenonq.spring.myrestaurant.controllers;

import com.fenonq.spring.myrestaurant.utils.Constants;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Locale;

@ControllerAdvice
public class GlobalController {

    @ModelAttribute("languages")
    public Locale[] populateUser() {
        return Constants.languages;
    }

}
