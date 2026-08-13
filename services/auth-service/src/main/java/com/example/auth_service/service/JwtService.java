package com.example.auth_service.service;

import com.example.auth_service.model.JwtProperties;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Date;

@Service
public class JwtService {

    private final PrivateKey privateKey;

    public JwtService(JwtProperties properties) {

        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");

            try (InputStream inputStream =
                         properties.path().getInputStream()) {

                keyStore.load(
                        inputStream,
                        properties.password().toCharArray()
                );
            }

            this.privateKey = (PrivateKey) keyStore.getKey(
                    properties.alias(),
                    properties.password().toCharArray()
            );

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Unable to load JWT signing key", e);
        }
    }

    public String generateToken(UserDetails userDetails) {

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis()
                                + 1000 * 60 * 30)
                )
                .signWith(privateKey)
                .compact();
    }
}