package com.example.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry){
                    registry.addMapping("/login")
//                            .allowedOrigins("http://localhost:4200", "http://localhost:5173")
                                    .allowedOrigins("https://upload-819f6.web.app/")
                                    .allowedMethods("*")
                                    .exposedHeaders("*");
                    registry.addMapping("/api/**")
//                            .allowedOrigins("http://localhost:4200", "http://localhost:5173")
                            .allowedOrigins("https://upload-819f6.web.app/")
//                            .allowedOrigins("https://sysdatademofront.web.app/")
                            .allowedMethods("*");
                }
            };
        }


}
