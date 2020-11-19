package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.service.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class JwtUtil {
    private static final String TYPE_TOKEN = "Bearer ";

    public static String getToken(final HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(TYPE_TOKEN)) {
            return authorizationHeader.substring(TYPE_TOKEN.length());
        }
        return null;
    }

    public static String generateToken(final UserDetails userDetails,
                                       final String secretKey,
                                       final Integer expiration) {
        log.debug("[generateToken]");
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(
            new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public static Boolean validateToken(final String token, final UserDetails userDetails, final String secretKey) {
        log.debug("[validateToken]");
        String username = extractUsername(token, secretKey);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, secretKey));
    }

    private static <T> T extractClaim(final String token,
                                      final Function<Claims, T> claimsResolver,
                                      final String secretKey) {
        log.debug("[extractClaim]");
        Claims claims = extractAllClaims(token, secretKey);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(final String token, final String secretKey) {
        log.debug("[extractClaim]");
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        } catch (Exception exception) {
            log.error("[{}]", exception.getMessage());
            throw new BusinessException("Wrong authorization");
        }
    }

    private static Boolean isTokenExpired(final String token, final String secretKey) {
        return extractExpiration(token, secretKey).before(new Date());
    }

    public static String extractUsername(final String token, final String secretKey) {
        log.debug("[extractUsername]");
        return extractClaim(token, Claims::getSubject, secretKey);
    }

    private static Date extractExpiration(final String token, final String secretKey) {
        log.debug("[extractExpiration]");
        return extractClaim(token, Claims::getExpiration, secretKey);
    }
}
