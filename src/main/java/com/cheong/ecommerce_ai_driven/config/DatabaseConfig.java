package com.cheong.ecommerce_ai_driven.config;

import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
@Slf4j
public class DatabaseConfig {

    public final R2dbcProperties r2dbcProperties;

    public DatabaseConfig(R2dbcProperties r2dbcProperties) {
        this.r2dbcProperties = r2dbcProperties;
    }

//    @Bean
//    @Primary
//    public PostgresqlConnectionFactory connectionFactory() {
//        return new PostgresqlConnectionFactory(
//                PostgresqlConnectionConfiguration.builder()
//                        .host("localhost")
//                        .database("biz_services_platform")
//                        .port(5432)
//                        .username(r2dbcProperties.getUsername())
//                        .password(r2dbcProperties.getPassword())
//                        .codecRegistrar(EnumCodec.builder().build())
//                        .build()
//        );
//    }

    @Bean
    public ConnectionFactoryInitializer initializeDatabase(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setEnabled(true);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        return initializer;

    }
}
