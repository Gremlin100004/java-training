package com.senla.socialnetwork.controller.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.socialnetwork.controller.exception.ControllerException;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.service.UserService;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.util.JwtUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class JwtFilter extends OncePerRequestFilter {
    private static final String INCORRECT_RESULT_SIZE_DATA_ACCESS_EXCEPTION_MESSAGE = "User is not logged in";
    private static final String EXCEPTION_MESSAGE = "Server error";
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${com.senla.socialnetwork.service.util.JwtUtil.secret-key:qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq}")
    private String secretKey;
    @Value("${com.senla.socialnetwork.controller.JwtUtil.expiration:3600000}")
    private Integer expiration;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) {
        log.debug("[doFilterInternal]");
        try {
            String username = null;
            String token = JwtUtil.getToken(request);
            if (token != null) {
                username = JwtUtil.extractUsername(token, secretKey);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null
                && !userService.getUserLogoutToken(username).equals(token)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (JwtUtil.validateToken(token, userDetails, secretKey)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            chain.doFilter(request, response);

        } catch (BusinessException exception) {
            log.error("[{}]", exception.getMessage());
            fillResponse(exception.getMessage(), response);
        } catch (IncorrectResultSizeDataAccessException exception) {
            log.error("[{}]", exception.getMessage());
            fillResponse(INCORRECT_RESULT_SIZE_DATA_ACCESS_EXCEPTION_MESSAGE, response);
        } catch (Exception exception) {
            log.error("[{}]", exception.getMessage());
            fillResponse(EXCEPTION_MESSAGE, response);
        }
    }

    private void fillResponse(String forClientMessage,  HttpServletResponse response) {
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(objectMapper.writeValueAsString(new ClientMessageDto(forClientMessage)));
        } catch (IOException ioException) {
            log.error("[{}]", ioException.getMessage());
            throw new ControllerException("Response error");
        }
    }

}
