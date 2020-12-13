package com.fastcode.timesheetapp5.security.sanitization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 *
 *         This class extends the WebMvcConfigurerAdapter which has a method add
 *         interceptor which tells the mvc to add a interceptor Which is created
 *         and passed by us
 *
 */
@Component
public class ProductServiceInterceptorAppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    ProductServiceInterceptor productServiceInterceptor;

    //that interceptors registered here only apply to controllers
    //   it intercept the request going to the controller
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(productServiceInterceptor);
    }
}
