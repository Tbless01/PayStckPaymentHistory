//package com.example.paymentIntegration.security;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//class CorsConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/api/v1/payment/initiate-payment")
//                .allowedOrigins("http://localhost:8080") // Replace with your allowed origins
//                .allowedMethods("GET", "POST") // Define allowed HTTP methods
//                .allowedHeaders("Authorization", "Content-Type"); // Define allowed headers
//    }
//}