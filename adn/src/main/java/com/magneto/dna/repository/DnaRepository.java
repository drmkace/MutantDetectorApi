package com.magneto.dna.repository;

import com.magneto.dna.entity.Dna;
import com.magneto.dna.config.Constants;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;

@Repository
public class DnaRepository {

    private final DynamoDbClient dbClient;

    public DnaRepository() {
        this.dbClient = DynamoDbClient.builder()
                .region(Region.US_EAST_2)
                .build();
    }

    public boolean save(Dna dnaEntity) {
        var values = new HashMap<String, AttributeValue>();

        values.put(Constants.DNA_TABLE_KEY, AttributeValue.builder().
                s(dnaEntity.getId())
                .build());
        values.put(Constants.DNA_TABLE_DNA_SAMPLE, AttributeValue.builder()
                .ss(dnaEntity.getDnaSequence())
                .build());
        values.put(Constants.DNA_TABLE_IS_MUTANT, AttributeValue.builder()
                .bool(dnaEntity.isMutant())
                .build());

        return this.putItem(values);
    }

    public Dna getById(String id) {
       var keyToGet = new HashMap<String, AttributeValue>();

        keyToGet.put(Constants.DNA_TABLE_KEY, AttributeValue.builder().s(id).build());

        var request = GetItemRequest.builder()
                .key(keyToGet)
                .tableName(Constants.DNA_TABLE_NAME)
                .build();
        var returnedItem = this.dbClient.getItem(request).item();

        if (returnedItem != null && !returnedItem.isEmpty()) {
            var dnaSampleList = returnedItem.get(Constants.DNA_TABLE_DNA_SAMPLE).ss();

            var dnaSample = new String[dnaSampleList.size()];
            dnaSample = dnaSampleList.toArray(dnaSample);

            return new Dna(dnaSample, returnedItem.get(Constants.DNA_TABLE_IS_MUTANT).bool());
        } else {
            return null;
        }
    }

    public long getHumanCount() {
        return getCount(false);
    }

    public long getMutantCount() {
        return getCount(true);
    }

    private boolean putItem(HashMap<String, AttributeValue> values) {
        PutItemRequest request = PutItemRequest.builder()
                .tableName(Constants.DNA_TABLE_NAME)
                .item(values)
                .build();
        try {
            this.dbClient.putItem(request);
            return true;
        } catch (ResourceNotFoundException ex) {
            //TODO: Log Exception
            return false;
        } catch (DynamoDbException ex) {
            //TODO: Log Exception
            return false;
        }
    }

    private long getCount(boolean isMutant) {
        var attributeNames = new HashMap<String, String>();
        var attributeValues = new HashMap<String, AttributeValue>();

        attributeNames.put("#isMutant", Constants.DNA_TABLE_IS_MUTANT);
        attributeValues .put(":isMutant", AttributeValue.builder().bool(isMutant).build());

        var scanRequest = ScanRequest.builder()
                .tableName(Constants.DNA_TABLE_NAME)
                .expressionAttributeNames(attributeNames)
                .expressionAttributeValues(attributeValues)
                .filterExpression("#isMutant = :isMutant")
                .build();
        try {
            var response = this.dbClient.scan(scanRequest);
            return  response.count();
        } catch (DynamoDbException ex) {
            // TODO: log Error
            return 0;
        }
    }
}
