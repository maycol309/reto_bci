package com.javatechie.crud.example.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.javatechie.crud.example.entity.UsuarioEntity;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    @Autowired
    private Key secretKey;

    /**
     *
     * @param authentication
     * @return
     */
    public String generateToken(Authentication authentication) {
    	UsuarioEntity userPrincipal = (UsuarioEntity) authentication.getPrincipal();
        byte[] secretBytes = secretKey.getEncoded();
        String base64SecretKey = Base64.getEncoder().encodeToString(secretBytes);
        return Jwts.builder().setSubject(userPrincipal.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, base64SecretKey)
                .compact();
    }

    public String generateToken(String username) {
        byte[] secretBytes = secretKey.getEncoded();
        String base64SecretKey = Base64.getEncoder().encodeToString(secretBytes);
        return Jwts.builder().setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, base64SecretKey)
                .compact();
    }

    /**
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        String username = getUsernameFromToken(token);
        byte[] secretBytes = secretKey.getEncoded();
        String base64SecretKey = Base64.getEncoder().encodeToString(secretBytes);
        return Jwts.builder().setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, base64SecretKey)
                .compact();
    }

    /**
     *
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        final byte[] secretBytes = secretKey.getEncoded();
        final String base64SecretKey = Base64.getEncoder().encodeToString(secretBytes);
        return Jwts.parser().setSigningKey(base64SecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     *
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        byte[] secretBytes = secretKey.getEncoded();
        String base64SecretKey = Base64.getEncoder().encodeToString(secretBytes);
        try {
            Jwts.parser().setSigningKey(base64SecretKey).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Bad formated token");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported token");
        } catch (SignatureException e) {
            log.error("Failed signature");
        }
        return false;
    }

    /**
     *
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        final byte[] secretBytes = secretKey.getEncoded();
        final String base64SecretKey = Base64.getEncoder().encodeToString(secretBytes);
        return Jwts.parser().setSigningKey(base64SecretKey).parseClaimsJws(token).getBody().getExpiration();
    }

    /**
     *
     * @param token
     * @return
     */
    public Date getIssuedAtDateFromToken(String token) {
        final byte[] secretBytes = secretKey.getEncoded();
        final String base64SecretKey = Base64.getEncoder().encodeToString(secretBytes);
        return Jwts.parser().setSigningKey(base64SecretKey).parseClaimsJws(token).getBody().getIssuedAt();
    }
}
