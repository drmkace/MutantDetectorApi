package com.magneto.adn.repository;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

import java.util.HashMap;

public abstract class BaseRepository {

    protected DynamoDbClient dbClient;

    public BaseRepository() {
        this.dbClient = DynamoDbClient.builder()
            .region(Region.US_EAST_2)
            .build();
    }

    protected abstract String getTableName();

    protected boolean putItem(HashMap<String, AttributeValue> values) {
        PutItemRequest request = PutItemRequest.builder()
                .tableName(this.getTableName())
                .item(values)
                .build();
        try {
            this.dbClient.putItem(request);
            return true;
        } catch (ResourceNotFoundException rnfex) {
            //TODO: Log Exception
            return false;
        } catch (DynamoDbException ddbex) {
            //TODO: Log Exception
            return false;
        }
    }
}
