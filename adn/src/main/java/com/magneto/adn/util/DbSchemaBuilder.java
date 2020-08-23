package com.magneto.adn.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DbSchemaBuilder {

    private static final Logger logger = LoggerFactory.getLogger(DbSchemaBuilder.class);

    private final DynamoDbClient dbClient;

    public DbSchemaBuilder() {
        this.dbClient = DynamoDbClient.builder()
                .region(Region.US_EAST_2)
                .build();
    }

    @PostConstruct
    public void CreateDb() {

        var tableNames= getAllTableNames();
        if(tableNames == null || tableNames.isEmpty()) {
            CreateDnaTable();
        }
        else{
            if(!tableNames.contains(Constants.DNA_TABLE_NAME)) {
                CreateDnaTable();
            }
        }
    }

    public boolean ping() {
        return this.getAllTableNames() != null;
    }

    private List<String> getAllTableNames() {

        ListTablesResponse response;
        try {
                ListTablesRequest request = ListTablesRequest.builder().build();
                response = this.dbClient.listTables(request);
                return response.tableNames();
            } catch (DynamoDbException ex) {
                logger.error(ex.getMessage(), ex);
            }
        return null;
    }

    private void CreateDnaTable() {
        var request = CreateTableRequest.builder()
                .attributeDefinitions(
                        AttributeDefinition.builder()
                            .attributeName(Constants.DNA_TABLE_KEY)
                            .attributeType(ScalarAttributeType.S)
                            .build())
                .keySchema(
                        KeySchemaElement.builder()
                                .attributeName(Constants.DNA_TABLE_KEY)
                                .keyType(KeyType.HASH)
                                .build())
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits(10L)
                        .writeCapacityUnits(10L)
                        .build())
                .tableName(Constants.DNA_TABLE_NAME)
                .build();

        try {
            this.dbClient.createTable(request);
        } catch (DynamoDbException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }
}
