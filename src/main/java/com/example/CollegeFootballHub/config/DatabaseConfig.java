package com.example.CollegeFootballHub.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.lang.NonNull;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.transaction.ReactiveTransactionManager;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.dialect.R2dbcDialect;

@Configuration
@EnableR2dbcRepositories
public class DatabaseConfig extends AbstractR2dbcConfiguration {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${spring.r2dbc.url}")
    private String url;

    @Value("${spring.r2dbc.username}")
    private String username;

    @Value("${spring.r2dbc.password}")
    private String password;

    @Bean
    @Override
    @NonNull
    public ConnectionFactory connectionFactory() {
        // Parse the R2DBC URL to extract host, port, and database
        // Format: r2dbc:postgresql://localhost:5432/collegefootball
        String[] parts = url.split("://")[1].split(":");
        String host = parts[0];
        String[] portAndDb = parts[1].split("/");
        int port = Integer.parseInt(portAndDb[0]);
        String database = portAndDb[1];

        // Create the PostgreSQL connection factory
        PostgresqlConnectionFactory connectionFactory = new PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host(host)
                        .port(port)
                        .database(database)
                        .username(username)
                        .password(password)
                        .build()
        );

        // Create connection pool configuration
        ConnectionPoolConfiguration poolConfig = ConnectionPoolConfiguration.builder()
                .connectionFactory(connectionFactory)
                .maxIdleTime(Duration.ofMinutes(30))
                .initialSize(5)
                .maxSize(10)
                .maxCreateConnectionTime(Duration.ofSeconds(5))
                .build();

        // Return the connection pool
        return new ConnectionPool(poolConfig);
    }

    @Bean
    @Override
    @NonNull
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Object> converters = new ArrayList<>();
        converters.add(new StringToListConverter());
        converters.add(new ListToStringConverter());

        // Cast the dialect to R2dbcDialect
        R2dbcDialect dialect = (R2dbcDialect) PostgresDialect.INSTANCE;
        return R2dbcCustomConversions.of(dialect, converters);
    }

    @Bean
    public ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("schema.sql"));
        initializer.setDatabasePopulator(populator);

        return initializer;
    }

    // Inner converter classes

    @ReadingConverter
    public static class StringToListConverter implements Converter<String, List<String>> {
        @Override
        public List<String> convert(String source) {
            if (source == null || source.isEmpty()) {
                return new ArrayList<>();
            }
            try {
                return objectMapper.readValue(source, new TypeReference<List<String>>() {});
            } catch (JsonProcessingException e) {
                // Fallback: try parsing comma-separated values
                List<String> result = new ArrayList<>();
                for (String part : source.split(",")) {
                    result.add(part.trim());
                }
                return result;
            }
        }
    }

    @WritingConverter
    public static class ListToStringConverter implements Converter<List<String>, String> {
        @Override
        public String convert(List<String> source) {
            if (source == null || source.isEmpty()) {
                return "[]";
            }
            try {
                return objectMapper.writeValueAsString(source);
            } catch (JsonProcessingException e) {
                // Fallback: create comma-separated values
                return String.join(",", source);
            }
        }
    }
}