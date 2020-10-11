package com.senla.carservice.controller.config;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

@Slf4j
public class LoggerInterceptor extends HandlerInterceptorAdapter {

    @SneakyThrows
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        String requestBody= IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        String requestBody= "";
//        request = requestCacheWrapperObject;
//        requestCacheWrapperObject.getParameterMap();
//        String strBody;
//        try {
//            strBody = IOUtils.toString(requestCacheWrapperObject.getInputStream());
//        } catch (IOException e) {
//            strBody = null;
//            e.printStackTrace();
//        }
        log.info("[preHandle][headers: {}][method: {}]{}{} [body: {}]", request.getHeaderNames().asIterator(), request.getMethod(),
                 request.getRequestURI(), getParameters(request), requestBody);
        return true;
    }

    @SneakyThrows
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(
        HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {

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
            log.debug("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }

}
