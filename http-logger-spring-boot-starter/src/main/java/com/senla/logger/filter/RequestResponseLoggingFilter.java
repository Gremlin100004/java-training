package com.senla.logger.filter;

import com.senla.logger.copier.CachedBodyHttpServletRequest;
import com.senla.logger.copier.HttpServletResponseCopier;
import com.senla.logger.exception.ControllerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Slf4j
public class RequestResponseLoggingFilter implements Filter {
    private static final String COMBINING_PARAMETERS = "&";
    private static final String PARAMETER_SYMBOL = "?";
    private static final String SWAGGER_URI = "/swagger-ui/";
    private static final String VALUE_PARAMETER_SYMBOL = "=";
    private static final String HTTP_HEADER_CLIENT_IP_ADDRESS = "X-FORWARDED-FOR";
    private static final String PROGRAM_SYSTEM_INFORMATION_PROTOCOL = "&__psip=";
    private static final String EMPTY_IP_ADDRESS = "";
    private static final int NUMBER_PARAMETERS = 1;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
        try {
            HttpServletResponseCopier responseCopier = new HttpServletResponseCopier((HttpServletResponse)response);
            CachedBodyHttpServletRequest requestCopier = new CachedBodyHttpServletRequest((HttpServletRequest)request);
            String requestBody = IOUtils.toString(requestCopier.getInputStream(), StandardCharsets.UTF_8);
            log.info("[request][headers: {}][method: {}][{}{}][body: {}]",
                     getList(requestCopier.getHeaderNames()), requestCopier.getMethod(),
                     requestCopier.getRequestURI(), getParameters((HttpServletRequest)request), requestBody);
            if (requestCopier.getRequestURI().contains(SWAGGER_URI)){
                filterChain.doFilter(request, response);
            } else {
                filterChain.doFilter(requestCopier, responseCopier);
            }
            log.info("[response][status: {}] [body: {}]", responseCopier.getStatus(),
                     new String(responseCopier.getCopy(), response.getCharacterEncoding()));
        } catch (Exception exception) {
            log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
            throw new ControllerException("Request error");
        }
    }

    private String getParameters(HttpServletRequest request) {
        StringBuilder posted = new StringBuilder();
        Enumeration<?> parameterNames = request.getParameterNames();
        if (parameterNames != null) {
            posted.append(PARAMETER_SYMBOL);
            while (parameterNames.hasMoreElements()) {
                if (posted.length() > NUMBER_PARAMETERS) {
                    posted.append(COMBINING_PARAMETERS);
                }
                String nextElement = (String) parameterNames.nextElement();
                posted.append(nextElement).append(VALUE_PARAMETER_SYMBOL);
                posted.append(request.getParameter(nextElement));
            }
        }
        String ip = request.getHeader(HTTP_HEADER_CLIENT_IP_ADDRESS);
        String ipAddress;
        if (ip == null) {
            ipAddress = getRemoteAddress(request);
        } else {
            ipAddress = ip;
        }
        if (ipAddress != null && !ipAddress.equals(EMPTY_IP_ADDRESS)) {
            posted.append(PROGRAM_SYSTEM_INFORMATION_PROTOCOL).append(ipAddress);
        }
        return posted.toString();
    }
    private String getRemoteAddress(HttpServletRequest request) {
        String ipFromHeader = request.getHeader(HTTP_HEADER_CLIENT_IP_ADDRESS);
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
