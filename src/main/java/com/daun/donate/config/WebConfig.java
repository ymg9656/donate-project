package com.daun.donate.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/vue/**")
                .addResourceLocations("classpath:/vue/");


        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
