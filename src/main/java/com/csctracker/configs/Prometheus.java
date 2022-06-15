package com.csctracker.configs;

import io.micrometer.core.instrument.MeterRegistry;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.FileReader;

@Component
public class Prometheus {
    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        var reader = new MavenXpp3Reader();
        var name = "CscTrackerCore";
        Model model = null;
        try {
            model = reader.read(new FileReader("pom.xml"));
            name = model.getName();
            System.out.println(model.getArtifactId());
            System.out.println(model.getVersion());
        } catch (Exception e) {
            //ignore
        }
        var finalName = name;
        return registry -> registry.config().commonTags("application", finalName);
    }
}
