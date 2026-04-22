package com.entralia.entralia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/eventos");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Para servir imágenes subidas por el usuario
        registry.addResourceHandler("/img/eventos/**")
                .addResourceLocations("file:C:/entralia/img/eventos/");

        // Para servir recursos estáticos del proyecto
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}

