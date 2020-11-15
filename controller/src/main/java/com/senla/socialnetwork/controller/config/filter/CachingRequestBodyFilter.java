package com.senla.socialnetwork.controller.config.filter;

import com.senla.socialnetwork.controller.exception.ControllerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Slf4j
public class CachingRequestBodyFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) {
        log.debug("[-------------it's work!!!!------------------]");
        log.debug("[doFilter]");
        try {
            ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
            chain.doFilter(wrappedRequest, servletResponse);
        } catch (IOException | ServletException exception) {
            log.error("[{}]", exception.getMessage());
            throw new ControllerException("Request error");
        }
    }

}
