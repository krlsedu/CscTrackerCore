package com.csctracker.configs;

import com.csctracker.service.UserInfoRemoteService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestInterceptor extends GenericFilterBean {

    public static boolean SECURE_REQUEST = false;
    private final UserInfoRemoteService userInfoRemoteService;

    public RequestInterceptor(UserInfoRemoteService userInfoRemoteService) {
        this.userInfoRemoteService = userInfoRemoteService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        ContentCaching wrappedRequest = new ContentCaching(request);
        if (!"OPTIONS".equals(request.getMethod()) && !SECURE_REQUEST) {
            try {
                wrappedRequest.setUser(userInfoRemoteService.getUser());
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            }
        }
        chain.doFilter(wrappedRequest, servletResponse);
    }
}
