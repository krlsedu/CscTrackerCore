package com.csctracker.service;

import com.csctracker.configs.ApplicationInfo;
import com.csctracker.dto.ServiceRegisterDTO;
import com.google.common.base.CaseFormat;
import kong.unirest.Unirest;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Getter
public class ServiceRegister<E> {
    private final ApplicationInfo applicationInfo;
    private final JpaRepository<E, Long> repository;
    private String serviceName;

    public ServiceRegister(ApplicationInfo applicationInfo, JpaRepository<E, Long> repository) {
        this.applicationInfo = applicationInfo;
        this.repository = repository;
        Class clazz = ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
        registerService(clazz);
    }

    public static <E> String getControllerName(Class<E> clazz) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName()).replace("_", "-");
    }

    private void registerService(Class<E> clazz) {
        var serviceRegisterDTO = new ServiceRegisterDTO();
        serviceRegisterDTO.setAppName(applicationInfo.getAppName());
        String serverName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, applicationInfo.getAppName());
        serverName = serverName.replace("_", "-");
        this.serviceName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName()).replace("_", "-");
        serviceRegisterDTO.setServiceName(serviceName);
        serviceRegisterDTO.setUrl("http://" + serverName + ":" + applicationInfo.getServerPort() + "/" + serviceName + "/");
        Map<String, String> attributes = new HashMap<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            attributes.put(declaredField.getName(), declaredField.getType().getSimpleName());
        }
        serviceRegisterDTO.setAttributes(attributes);
        try {
            log.info(Unirest.post(applicationInfo.getServiceHost() + ":" + applicationInfo.getServicePort() + "/service")
                    .header("Content-Type", "application/json")
                    .body(serviceRegisterDTO)
                    .asString());
        } catch (Exception e) {
            log.error(e);
        }
    }

    public E save(E entity) {
        return repository.save(entity);
    }

    public void delete(E entity) {
        repository.delete(entity);
    }
}
