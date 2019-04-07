/*
 *
 *  * COPYRIGHT (c). HSBC HOLDINGS PLC 2019. ALL RIGHTS RESERVED.
 *  *
 *  * This software is only to be used for the purpose for which it has been
 *  * provided. No part of it is to be reproduced, disassembled, transmitted,
 *  * stored in a retrieval system nor translated in any human or computer
 *  * language in any way or for any other purposes whatsoever without the prior
 *  * written consent of HSBC Holdings plc.
 *
 */

package com.demo.jwt.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter {
    @Value("${jwt.header.key}")
    private String jwtHeader;
    @Value("${jwt.cert.path}")
    private String certPath;

    @Autowired
    ResourceLoader resourceLoader;

    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setOrder(0);
        registrationBean.setFilter(new JwtFilter(resourceLoader, jwtHeader, certPath));
        registrationBean.addUrlPatterns("/api/*");

        return registrationBean;
    }

    public void setJwtHeader(String jwtHeader) {
        this.jwtHeader = jwtHeader;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }
}
