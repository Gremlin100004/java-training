package com.senla.socialnetwork.controller.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.socialnetwork.controller.config.SigningKey;
import com.senla.socialnetwork.controller.exception.ControllerException;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.util.JwtUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private static final String INCORRECT_RESULT_SIZE_DATA_ACCESS_EXCEPTION_MESSAGE = "User is not logged in";
    private static final String EXCEPTION_MESSAGE = "Server error";
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    SigningKey signingKey;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain chain) {
        log.info("[security check]");
        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        } catch (BusinessException exception) {
            log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
            fillResponse(exception.getMessage(), response);
        } catch (IncorrectResultSizeDataAccessException exception) {
            log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
            fillResponse(INCORRECT_RESULT_SIZE_DATA_ACCESS_EXCEPTION_MESSAGE, response);
        } catch (Exception exception) {
            log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
            fillResponse(EXCEPTION_MESSAGE, response);
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = JwtUtil.getToken(request);
        if (token == null) {
            return null;
        }
        String username = JwtUtil.extractUsername(token, signingKey.getSecretKey());
        if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            return null;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!JwtUtil.validateToken(token, userDetails, signingKey.getSecretKey())) {
            return null;
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }

    private void fillResponse(String forClientMessage,  HttpServletResponse response) {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(objectMapper.writeValueAsString(new ClientMessageDto(forClientMessage)));
        } catch (IOException exception) {
            log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
            throw new ControllerException("Response error");
        }
    }

}
