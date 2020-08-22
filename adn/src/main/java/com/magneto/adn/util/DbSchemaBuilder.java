package com.magneto.adn.util;

import org.apache.tomcat.util.bcel.Const;
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

    private DynamoDbClient dbClient;

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
            CreateStatTable();
        }
        else{
            if(!tableNames.contains(Constants.DNA_TABLE_NAME)) {
                CreateDnaTable();
            }
            if(!tableNames.contains(Constants.STAT_TABLE_NAME)) {
                CreateStatTable();
            }
        }
    }

    public boolean ping() {
        return this.getAllTableNames() != null;
    }

    private List<String> getAllTableNames() {

        ListTablesResponse response = null;
        List<String> tableNames = null;
        try {
                ListTablesRequest request = ListTablesRequest.builder().build();
                response = this.dbClient.listTables(request);
                tableNames = response.tableNames();
                return tableNames;
            } catch (DynamoDbException ex) {
                logger.error(ex.getMessage(), ex);
            }
        finally {
            return tableNames;
        }
    }

    private void CreateStatTable() {
        var request = CreateTableRequest.builder()
                .attributeDefinitions(AttributeDefinition.builder()
                        .attributeName(Constants.STAT_TABLE_KEY)
                        .attributeType(ScalarAttributeType.S)
                        .build())
                .keySchema(KeySchemaElement.builder()
                        .attributeName(Constants.STAT_TABLE_KEY)
                        .keyType(KeyType.HASH)
                        .build())
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits(10L)
                        .writeCapacityUnits(10L)
                        .build())
                .tableName(Constants.STAT_TABLE_NAME)
                .build();

        try {
            this.dbClient.createTable(request);
        } catch (DynamoDbException ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    private void CreateDnaTable() {
        var request = CreateTableRequest.builder()
                .attributeDefinitions(AttributeDefinition.builder()
                        .attributeName(Constants.DNA_TABLE_KEY)
                        .attributeType(ScalarAttributeType.S)
                        .build())
                .keySchema(KeySchemaElement.builder()
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
