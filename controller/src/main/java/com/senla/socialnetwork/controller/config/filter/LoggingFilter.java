package com.senla.socialnetwork.controller.config.filter;

import com.senla.socialnetwork.controller.config.copier.CachedBodyHttpServletRequest;
import com.senla.socialnetwork.controller.config.copier.HttpServletResponseCopier;
import com.senla.socialnetwork.controller.exception.ControllerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@WebFilter(filterName = "LoggingFilter", urlPatterns = "/*")
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {
    private static final String COMBINING_PARAMETERS = "&";
    private static final String PARAMETER_SYMBOL = "?";
    private static final String VALUE_PARAMETER_SYMBOL = "=";
    private static final String HTTP_HEADER_CLIENT_IP_ADDRESS = "X-FORWARDED-FOR";
    private static final String PROGRAM_SYSTEM_INFORMATION_PROTOCOL = "&__psip=";
    private static final String EMPTY_IP_ADDRESS = "";
    private static final int NUMBER_PARAMETERS = 1;

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
        } catch (Exception exception) {
            log.error("[{}]", exception.getMessage());
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
                String curr = (String) parameterNames.nextElement();
                posted.append(curr).append(VALUE_PARAMETER_SYMBOL);
                posted.append(request.getParameter(curr));
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
