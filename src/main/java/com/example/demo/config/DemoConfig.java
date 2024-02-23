package com.example.demo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:application.yaml")
@EnableTransactionManagement
public class DemoConfig {
    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }

}
