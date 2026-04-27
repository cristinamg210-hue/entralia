package com.entralia.entralia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Esta clase contiene configuración de Spring
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        // Cuando alguien entra a "/", lo mando a /eventos
        registry.addRedirectViewController("/", "/eventos");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Sirve imágenes subidas por el usuario desde esta carpeta del disco
        registry.addResourceHandler("/img/eventos/**")
                .addResourceLocations("file:C:/entralia/img/eventos/");

        // Sirve archivos estáticos del proyecto (CSS, imágenes internas, etc.)
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}

