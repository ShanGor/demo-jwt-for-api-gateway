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
package com.demo.jwt.server.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.PublicKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * Token API filter
 */
public class JwtFilter extends GenericFilterBean {
    private String jwtHeader;
    private String certPath;
    private ResourceLoader resourceLoader;

    private JwtFilter(){}
    public JwtFilter(ResourceLoader resourceLoader, String jwtHeader, String certPath) {
        setCertPath(certPath);
        setJwtHeader(jwtHeader);
        setResourceLoader(resourceLoader);
    }

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        final String authHeader = request.getHeader(jwtHeader);
        if (authHeader == null) {
            writeAccessDenied(response, "Missing "+jwtHeader+" header.");
            return;
        }

        final String token = authHeader;
        logger.info("Token: " + token);

        try {
            Jwts.parser()
                    .setSigningKey(getCert())
                    .parseClaimsJws(token)
                    .getBody();
            logger.info("JWT validated successfully");
        } catch (JwtException|CertificateException e) {
            logger.info("JWT validated failed: " + e.getMessage());

            if (e instanceof ExpiredJwtException) {
                writeAccessDenied(response, "Token Expired: " + token);
            } else {
                writeAccessDenied(response, "Invalid Token: " + token);
            }
            return;
        }

        chain.doFilter(req, res);
    }

    PublicKey getCert() throws CertificateException, IOException {
        InputStream ins = resourceLoader.getResource(certPath).getInputStream();
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(ins);
        PublicKey publicKey = cert.getPublicKey();
        ins.close();
        return publicKey;
    }

    void writeAccessDenied(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(message);
    }

    public String getCertPath() {
        return certPath;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }

    public String getJwtHeader() {
        return jwtHeader;
    }

    public void setJwtHeader(String jwtHeader) {
        this.jwtHeader = jwtHeader;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
