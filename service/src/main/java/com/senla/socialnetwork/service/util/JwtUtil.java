package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.service.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public final class JwtUtil {
    private static final String TYPE_TOKEN = "Bearer ";

    private JwtUtil() {
    }

    public static String getToken(final HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(TYPE_TOKEN)) {
            return authorizationHeader.substring(TYPE_TOKEN.length());
        }
        return null;
    }

    public static String generateToken(final UserDetails userDetails,
                                       final SecretKey secretKey,
                                       final Integer expiration) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(
            new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(secretKey, SignatureAlgorithm.HS256).compact();
    }

    public static Boolean validateToken(final String token, final UserDetails userDetails, final SecretKey secretKey) {
        String username = extractUsername(token, secretKey);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, secretKey));
    }

    public static String extractUsername(final String token, final SecretKey secretKey) {
        return extractClaim(token, Claims::getSubject, secretKey);
    }

    private static Date extractExpiration(final String token, final SecretKey secretKey) {
        return extractClaim(token, Claims::getExpiration, secretKey);
    }

    private static <T> T extractClaim(final String token,
                                      final Function<Claims, T> claimsResolver,
                                      final SecretKey secretKey) {
        Claims claims = extractAllClaims(token, secretKey);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(final String token, final SecretKey secretKey) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        } catch (Exception exception) {
            log.error("[{}]", exception.getMessage());
            throw new BusinessException("Wrong authorization");
        }
    }

    private static Boolean isTokenExpired(final String token, final SecretKey secretKey) {
        return extractExpiration(token, secretKey).before(new Date());
    }

}
