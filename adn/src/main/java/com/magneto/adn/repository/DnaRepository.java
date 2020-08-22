package com.magneto.adn.repository;

import com.magneto.adn.entity.Dna;
import com.magneto.adn.util.Constants;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

import java.util.HashMap;

@Repository
public class DnaRepository extends BaseRepository {

    @Override
    protected String getTableName() {
        return Constants.DNA_TABLE_NAME;
    }

    public boolean save(Dna dnaEntity) {
        var values = new HashMap<String, AttributeValue>();

        values.put(Constants.DNA_TABLE_KEY, AttributeValue.builder().
                s(dnaEntity.getId())
                .build());
        values.put(Constants.DNA_TABLE_DNASAMPLE, AttributeValue.builder()
                .ss(dnaEntity.getDnaSequence())
                .build());
        values.put(Constants.DNA_TABLE_ISMUTANT, AttributeValue.builder()
                .bool(dnaEntity.isMutant())
                .build());

        return this.putItem(values);
    }

    public Dna getById(String id) {
       var keyToGet = new HashMap<String, AttributeValue>();

        keyToGet.put(Constants.DNA_TABLE_KEY, AttributeValue.builder().s(id).build());

        var request = GetItemRequest.builder()
                .key(keyToGet)
                .tableName(this.getTableName())
                .build();
        var returnedItem = this.dbClient.getItem(request).item();

        if (returnedItem != null && !returnedItem.isEmpty()) {
            var dnaSampleList = returnedItem.get(Constants.DNA_TABLE_DNASAMPLE).ss();

            var dnaSample = new String[dnaSampleList.size()];
            dnaSample = dnaSampleList.toArray(dnaSample);

            return new Dna(dnaSample, returnedItem.get(Constants.DNA_TABLE_ISMUTANT).bool());
        } else {
            return null;
        }
    }
}
