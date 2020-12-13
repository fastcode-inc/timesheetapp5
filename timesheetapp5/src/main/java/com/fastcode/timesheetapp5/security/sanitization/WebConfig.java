package com.fastcode.timesheetapp5.security.sanitization;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // the list is empty, so we just add our converter
        converters.add(jsonConverter());
    }

    @Bean
    public HttpMessageConverter<Object> jsonConverter() {
        System.out.println("output sanitization bean method");
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder
            .json()
            .serializerByType(String.class, new SanitizedStringSerializer())
            .build();
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }
}
