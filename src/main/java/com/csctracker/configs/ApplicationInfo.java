package com.csctracker.configs;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileReader;

@Getter
@Component
@Slf4j
public class ApplicationInfo {
    @Value("${spring.application.name}")
    private String appName;
    private static String applicationName = null;
    private static String applicationVersion = null;
    @Value("${server.port}")
    private String serverPort;
    @Value("${service.host:htto://service}")
    private String serviceHost;
    @Value("${service.port:5000}")
    private String servicePort;

    public static String getApplicationVersion() {
        if (applicationVersion == null) {
            initNames();
        }
        return applicationVersion;
    }

    public static String getApplicationName() {
        if (applicationName == null) {
            initNames();
        }
        return applicationName;
    }


    public static void initNames() {
        var reader = new MavenXpp3Reader();
        try (FileReader fileReader = new FileReader("pom.xml")) {
            var model = reader.read(fileReader);
            applicationVersion = model.getVersion();
            applicationName = model.getArtifactId();
        } catch (Exception e) {
            log.error("Error reading pom.xml: {}", e.getMessage());
        }
    }
}
