package com.mycompany.todoapi.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.time.Instant;
import java.util.function.Supplier;

@Configuration
public class ApplicationConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*").allowedHeaders("*");
            }
        };
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway (@Qualifier("dataSource") DataSource dataSource) {
        return new Flyway(Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).locations("/db"));
    }

    @Bean
    public Supplier<Long> instantSupplier() {
        return () -> Instant.now().toEpochMilli();
    }
}
