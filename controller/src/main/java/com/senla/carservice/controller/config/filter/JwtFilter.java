package com.senla.carservice.controller.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.carservice.controller.exception.ControllerException;
import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.service.util.JwtUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@NoArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private static final String TYPE_TOKEN = "Bearer ";
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${com.senla.carservice.JwtUtil.secret-key:qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq}")
    private String secretKey;
    @Value("${com.senla.carservice.controller.JwtUtil.expiration:3600000}")
    private Integer expiration;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        log.debug("[doFilterInternal]");
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String username = null;
        String jwt = null;
        try {
            if (authorizationHeader != null && authorizationHeader.startsWith(TYPE_TOKEN)) {
                jwt = authorizationHeader.substring(TYPE_TOKEN.length());
                username = JwtUtil.extractUsername(jwt, secretKey);
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (JwtUtil.validateToken(jwt, userDetails, secretKey)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            chain.doFilter(request, response);
        } catch (Exception exception) {
            log.error("[{}]", exception.getMessage());
            try {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                response.getWriter().write(objectMapper.writeValueAsString(new ClientMessageDto("Server error")));
            } catch (IOException ioException) {
                log.error("[{}]", exception.getMessage());
                throw new ControllerException("Response error");
            }
        }
    }

}
