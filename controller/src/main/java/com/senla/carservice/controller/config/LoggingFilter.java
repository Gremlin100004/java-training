package com.senla.carservice.controller.config;

import com.senla.carservice.controller.exception.ControllerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@WebFilter(filterName = "LoggingFilter", urlPatterns = "/*")
@Slf4j
public class LoggingFilter extends CommonsRequestLoggingFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) {
        try {
            HttpServletResponseCopier responseCopier = new HttpServletResponseCopier(httpServletResponse);
            CachedBodyHttpServletRequest requestCopier = new CachedBodyHttpServletRequest(httpServletRequest);
            filterChain.doFilter(requestCopier, responseCopier);
            String requestBody = IOUtils.toString(requestCopier.getInputStream(), StandardCharsets.UTF_8);
            log.info("[request][headers: {}][method: {}][{}{}][body: {}]",
                getList(requestCopier.getHeaderNames()), requestCopier.getMethod(),
                requestCopier.getRequestURI(), getParameters(httpServletRequest), requestBody);
            log.info("[response][status: {}] [body: {}]", responseCopier.getStatus(),
                new String(responseCopier.getCopy(), httpServletResponse.getCharacterEncoding()));
        } catch (IOException | ServletException e) {
            throw new ControllerException("Request error");
        }
    }

    private String getParameters(HttpServletRequest request) {
        StringBuilder posted = new StringBuilder();
        Enumeration<?> parameterNames = request.getParameterNames();
        if (parameterNames != null) {
            posted.append("?");
            while (parameterNames.hasMoreElements()) {
                if (posted.length() > 1) {
                    posted.append("&");
                }
                String curr = (String) parameterNames.nextElement();
                posted.append(curr).append("=");
                posted.append(request.getParameter(curr));
            }
        }
        String ip = request.getHeader("X-FORWARDED-FOR");
        String ipAddress = (ip == null) ? getRemoteAddress(request) : ip;
        if (ipAddress != null && !ipAddress.equals("")) {
            posted.append("&__psip=").append(ipAddress);
        }
        return posted.toString();
    }
    private String getRemoteAddress(HttpServletRequest request) {
        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if (ipFromHeader != null && ipFromHeader.length() > 0) {
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }

    private List<String> getList(Enumeration<String> enumeration) {
        List<String> stringList = new ArrayList<>();
        while (enumeration.hasMoreElements()) {
            stringList.add(enumeration.nextElement());
        }
        return stringList;
    }

}
