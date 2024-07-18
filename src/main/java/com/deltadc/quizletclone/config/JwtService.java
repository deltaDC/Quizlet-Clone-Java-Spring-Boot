package com.deltadc.quizletclone.config;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/*
JwtService để thực hiện các service liên quan đến token
 */


@Service
public class JwtService {

    private JWSSigner signer;
    private JWSVerifier verifier;

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);
    @Value("${app.secretkey}")
    private String SECRET_KEY;

    public String extractUsername(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            if (signedJWT.verify(verifier)) {
                return signedJWT.getJWTClaimsSet().getSubject();
            }
        } catch (Exception e) {
            log.error("Failed to extract username from token", e);
        }
        return null;
    }

    @PostConstruct
    public void init() throws JOSEException {
        signer = new MACSigner(SECRET_KEY);
        verifier = new MACVerifier(SECRET_KEY);
    }

    public String generateToken(UserDetails userDetails) throws JOSEException {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(userDetails.getUsername())
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .claim("roles", userDetails.getAuthorities())
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    public JWTClaimsSet extractAllClaims(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        if (!signedJWT.verify(verifier)) {
            throw new JOSEException("Signature verification failed");
        }
        return signedJWT.getJWTClaimsSet();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            JWTClaimsSet claims = extractAllClaims(token);
            return claims.getSubject().equals(userDetails.getUsername()) && !claims.getExpirationTime().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
