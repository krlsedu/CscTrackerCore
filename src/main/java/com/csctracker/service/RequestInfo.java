package com.csctracker.service;

import com.csctracker.configs.ContentCaching;
import com.csctracker.configs.UnAuthorized;
import com.csctracker.model.User;
import com.mashape.unirest.http.HttpResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

    public static User getUser() {
        return getUser(getRequest());
    }

    public static User getUser(HttpServletRequest request) {
        var requestWrapper = (ContentCaching) request;
        return requestWrapper.getUser();
    }

    public static String getBody(HttpServletRequest request) {
        var requestWrapper = (ContentCaching) request;
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

    public static void checkResponse(HttpResponse<String> response) {
        if (response.getStatus() < 200 || response.getStatus() > 299) {
            switch (response.getStatus()) {
                case 401:
                case 403:
                    throw new UnAuthorized();
                case 404:
                    throw new RuntimeException("Not Found");
                default:
                    throw new RuntimeException("Internal Server Error");
            }
        }
    }
}
