package com.proje.salad_App.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Allow requests from any origin for GET requests
     //   config.addAllowedOrigin("*");
      //  config.addAllowedMethod("GET");

        // You can add more methods here if needed
     //    config.addAllowedMethod("POST");
        // config.addAllowedMethod("PUT");
        // config.addAllowedMethod("DELETE");

        // Allow all headers
       // config.addAllowedHeader("*");
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*"); // Tüm HTTP yöntemlerine izin ver
        config.addAllowedHeader("*"); // Tüm başlıklara izin ver

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);

    }
}


