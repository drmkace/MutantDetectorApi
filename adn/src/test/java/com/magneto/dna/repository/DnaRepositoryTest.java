package com.magneto.dna.repository;

import com.magneto.dna.config.Constants;
import com.magneto.dna.entity.Dna;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Arrays;
import java.util.HashMap;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DnaRepositoryTest {

    final static String[] SAMPLE_MUTANT_DNA = {"AAAA", "AAAA", "AAAA", "AAAA"};

    @Mock
    DynamoDbClient dbClient;

//    @Mock
//    Environment environmentMock;

    @InjectMocks
    DnaRepository dnaRepository;

    @Test
    public void saveSuccess() {
        //Conditions
        var dnaEntityMock = mock(Dna.class);

        when(dnaEntityMock.getDnaSequence()).thenReturn(SAMPLE_MUTANT_DNA);
        when(dbClient.putItem(any(PutItemRequest.class))).thenReturn(null);

        //Test
        var result = dnaRepository.save(dnaEntityMock);

        //Verifies
        assert (result);
    }

    @Test
    public void saveFails() {
        //Conditions
        var dnaEntityMock = mock(Dna.class);

        when(dnaEntityMock.getDnaSequence()).thenReturn(SAMPLE_MUTANT_DNA);
        when(dbClient.putItem(any(PutItemRequest.class))).thenThrow(DynamoDbException.class);

        //Test
        var result = dnaRepository.save(dnaEntityMock);

        //Verifies
        assert (!result);
    }

    @Test
    public void getByIdEmptyItemReturnsNull() {
        //Conditions
        var itemMock = mock(HashMap.class);
        var getItemResponseMock = mock(GetItemResponse.class);

        when(dbClient.getItem(any(GetItemRequest.class))).thenReturn(getItemResponseMock);
        when(getItemResponseMock.item()).thenReturn(itemMock);
        when(itemMock.isEmpty()).thenReturn(true);

        //Test
        var result = dnaRepository.getById("1000");

        //Verifies
        assert (result == null);
    }

    @Test
    public void getByIdNullItemReturnsNull() {
        //Conditions
        var getItemResponseMock = mock(GetItemResponse.class);

        when(dbClient.getItem(any(GetItemRequest.class))).thenReturn(getItemResponseMock);
        when(getItemResponseMock.item()).thenReturn(null);

        //Test
        var result = dnaRepository.getById("1000");

        //Verifies
        assert (result == null);
    }

    @Test
    public void getByIdReturnsDnaEntitySuccess() {
        //Conditions
        var itemMock = mock(HashMap.class);
        var getItemResponseMock = mock(GetItemResponse.class);
        var attributeMock = mock(AttributeValue.class);

        when(dbClient.getItem(any(GetItemRequest.class))).thenReturn(getItemResponseMock);
        when(getItemResponseMock.item()).thenReturn(itemMock);
        when(itemMock.isEmpty()).thenReturn(false);
        when(itemMock.get(Constants.DNA_TABLE_DNA_SAMPLE)).thenReturn(attributeMock);
        when(itemMock.get(Constants.DNA_TABLE_IS_MUTANT)).thenReturn(attributeMock);
        when(attributeMock.ss()).thenReturn(Arrays.asList(SAMPLE_MUTANT_DNA));
        when(attributeMock.bool()).thenReturn(true);

        //Test
        var result = dnaRepository.getById("1000");

        //Verifies
        assert (result != null);
    }

    @Test
    public void getHumanCountSuccess() {
        //Conditions
        var scanResponseMock = mock(ScanResponse.class);

        when(dbClient.scan(any(ScanRequest.class))).thenReturn(scanResponseMock);
        when(scanResponseMock.count()).thenReturn(10);

        //Test
        var count = dnaRepository.getHumanCount();

        //Verifies
        assert (count >= 0);
    }

    @Test
    public void getMutantCountSuccess() {
        //Conditions
        var scanResponseMock = mock(ScanResponse.class);

        when(dbClient.scan(any(ScanRequest.class))).thenReturn(scanResponseMock);
        when(scanResponseMock.count()).thenReturn(10);

        //Test
        var count = dnaRepository.getMutantCount();

        //Verifies
        assert (count >= 0);
    }

    @Test
    public void getHumanCountThrowDynamoDbException() {
        //Conditions
        when(dbClient.scan(any(ScanRequest.class))).thenThrow(DynamoDbException.class);

        //Test
        var count = dnaRepository.getHumanCount();

        //Verifies
        assert (count == 0);
    }

    @Test
    public void getMutantCountThrowDynamoDbException() {
        //Conditions
        when(dbClient.scan(any(ScanRequest.class))).thenThrow(DynamoDbException.class);

        //Test
        var count = dnaRepository.getMutantCount();

        //Verifies
        assert (count == 0);
    }
}
