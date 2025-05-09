package com.example.fashion_spring_boot.service.localization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // Cấu hình ngôn ngữ mặc định
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver locale = new SessionLocaleResolver();
        locale.setDefaultLocale(Locale.ENGLISH);
        return locale;
    }

    // Cấu hình interceptor để đổi ngôn ngữ qua ?lang=
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang"); // truyền lang=vi hoặc lang=en
        return interceptor;
    }

    // Đăng ký interceptor
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    // Cấu hình đọc file messages.properties
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages"); // Đảm bảo đúng tên file
        source.setDefaultEncoding("UTF-8");
        source.setCacheSeconds(3600);  // Thêm thời gian cache (1 tiếng)
        return source;
    }
}
