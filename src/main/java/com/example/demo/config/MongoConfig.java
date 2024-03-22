package com.example.demo.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.demo.repos")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected @NonNull String getDatabaseName() {
        return "mongodb";
    }

    @Override
    public @NonNull MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:9092");
    }
}
