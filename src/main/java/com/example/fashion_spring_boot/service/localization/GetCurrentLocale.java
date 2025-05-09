package com.example.fashion_spring_boot.service.localization;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class GetCurrentLocale {
    public String currentLocale(String lang) {
        // Lấy ngôn ngữ hiện tại nếu không có lang trên URL
        Locale currentLocale = LocaleContextHolder.getLocale();
        return (lang != null) ? lang : currentLocale.getLanguage();
    }
}
