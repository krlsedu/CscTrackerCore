package com.csctracker.utils;

import com.csctracker.model.ServiceError;
import com.csctracker.repository.ServiceErrorRepository;
import kong.unirest.GetRequest;
import kong.unirest.HttpRequestWithBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import static com.csctracker.configs.CustomHttpTraceFilter.CORRELATION_ID_LOG_VAR_NAME;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceErrorUtil {
    private final ServiceErrorRepository serviceErrorRepository;

    public void save(Exception exception, HttpRequestWithBody request) {
        save(exception, request, null);
    }

    public void save(Exception exception, HttpRequestWithBody request, String body) {
        try {
            var serviceError = new ServiceError();
            serviceError.setMessage(exception.getMessage());
            var url = request.getUrl();
            var urlParts = url.split("/");
            var service = urlParts[2].split(":")[0];
            serviceError.setService(service);
            if (body != null) {
                serviceError.setBody(body);
            } else {
                serviceError.setBody(request.getBody().toString());
            }
            serviceError.setHeaders(request.getHeaders().toString());
            serviceError.setMethod(request.getHttpMethod().name());
            serviceError.setUrl(url);
            serviceError.setDateTime(new java.util.Date());
            serviceError.setResponse(request.asString().getBody());
            serviceError.setRequestId(MDC.get(CORRELATION_ID_LOG_VAR_NAME));
            serviceErrorRepository.save(serviceError);
        } catch (Exception e) {
            log.error("Error saving error: {}", e.getMessage());
        }
    }

    public void save(Exception exception, GetRequest request) {
        save(exception, request, null);
    }

    public void save(Exception exception, GetRequest request, String body) {
        try {
            var serviceError = new ServiceError();
            serviceError.setMessage(exception.getMessage());
            var url = request.getUrl();
            var urlParts = url.split("/");
            var service = urlParts[2].split(":")[0];
            serviceError.setService(service);
            if (body != null) {
                serviceError.setBody(body);
            } else {
                serviceError.setBody(request.getBody().toString());
            }
            serviceError.setHeaders(request.getHeaders().toString());
            serviceError.setMethod(request.getHttpMethod().name());
            serviceError.setUrl(url);
            serviceError.setDateTime(new java.util.Date());
            serviceError.setResponse(request.asString().getBody());
            serviceError.setRequestId(MDC.get(CORRELATION_ID_LOG_VAR_NAME));
            serviceErrorRepository.save(serviceError);
        } catch (Exception e) {
            log.error("Error saving error: {}", e.getMessage());
        }
    }

    public void save(Exception exception, String service, String url) {
        try {
            var serviceError = new ServiceError();
            serviceError.setMessage(exception.getMessage());
            serviceError.setService(service);
            serviceError.setUrl(url);
            serviceError.setDateTime(new java.util.Date());
            serviceError.setRequestId(MDC.get(CORRELATION_ID_LOG_VAR_NAME));
            serviceErrorRepository.save(serviceError);
        } catch (Exception e) {
            log.error("Error saving error: {}", e.getMessage());
        }
    }
}
