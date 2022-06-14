package com.csctracker.configs;

import com.csctracker.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;

@Setter
@Getter
public class ContentCaching extends ContentCachingRequestWrapper {

    private User user;

    public ContentCaching(HttpServletRequest request) {
        super(request);
    }

    public ContentCaching(HttpServletRequest request, int contentCacheLimit) {
        super(request, contentCacheLimit);
    }

}
