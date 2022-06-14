package com.csctracker.service;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestInfo {
    public static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static String getBody() {
        return getBody(getRequest());
    }

    public static String getBody(HttpServletRequest request) {
        var requestWrapper = (ContentCachingRequestWrapper) request;
        return new String(requestWrapper.getContentAsByteArray());
    }

    public static HttpServletRequest getRequest() {
        if (getServletRequestAttributes() == null) {
            throw new RuntimeException("Exception: ServletRequestAttributes is null");
        }
        return getServletRequestAttributes().getRequest();
    }

    public static String getParametro(String nome) {
        HttpServletRequest request = getRequest();
        Enumeration<String> it = request.getParameterNames();
        while (it.hasMoreElements()) {
            String st = it.nextElement();
            if (st.equalsIgnoreCase(nome)) {
                return request.getParameter(st);
            }
        }
        return null;
    }

    public static String getHeader(String nome) {
        HttpServletRequest request = getRequest();
        Enumeration<String> it = request.getHeaderNames();
        while (it.hasMoreElements()) {
            String st = it.nextElement();
            if (st.equalsIgnoreCase(nome)) {
                return request.getHeader(st);
            }
        }
        return null;
    }

    public static Map<String, String> getHeaders() {
        HttpServletRequest request = getRequest();
        Enumeration<String> it = request.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (it.hasMoreElements()) {
            String st = it.nextElement();
            headers.put(st, request.getHeader(st));
        }
        return headers;
    }

    public static Map<String, String> getParameters() {
        HttpServletRequest request = getRequest();
        Enumeration<String> it = request.getParameterNames();
        Map<String, String> headers = new HashMap<>();
        while (it.hasMoreElements()) {
            String st = it.nextElement();
            headers.put(st, request.getParameter(st));
        }
        return headers;
    }

    public static String getPath() {
        return getRequest().getRequestURI();
    }
}
