package com.example.EmployeeManagement.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "cors")
public class CorsProperty {

    private List<String> allowedHeaders;
    private List<String> allowedOrigins;
    private List<String> allowedMethods;
    private String urlPattern;
    private boolean allowCredentials;

}
