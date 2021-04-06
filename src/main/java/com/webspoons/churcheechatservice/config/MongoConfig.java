package com.webspoons.churcheechatservice.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableMongoRepositories(basePackages = "com.webspoons.churcheechatservice.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${db.mongo.uri}")
    private String  MONGO_DB_URI;

    @Value("${db.mongo.churchee}")
    private String MONGO_DB_NAME;

    @Override
    protected String getDatabaseName() {
        return MONGO_DB_NAME;
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(MONGO_DB_URI);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public Collection getMappingBasePackages() {
        return Collections.singleton("com.webspoons.churcheechatservice.model");
    }

//    @Override
//    public boolean autoIndexCreation() {
//        return true;
//    }
}
