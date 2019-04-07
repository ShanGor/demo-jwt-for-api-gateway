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

package com.demo.jwt.server.controller;

import com.demo.jwt.server.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    ResourceLoader resourceLoader;
    @Value("${jwt.keystore.password}")
    String keystorePassword;
    @Value("${jwt.keystore.alias}")
    String keyAlias;
    @Value("${jwt.keystore.path}")
    String keystorePath;

    @RequestMapping("/jwt")
    public String testJwt() {
        return new JwtUtil(resourceLoader, keystorePath, keystorePassword, keyAlias).generateJwt();
    }

    @RequestMapping("/health")
    public String health() {
        return "good";
    }
}
