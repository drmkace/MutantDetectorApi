package com.magneto.dna.service;

import com.magneto.dna.entity.Dna;
import com.magneto.dna.exception.InvalidDnaException;
import com.magneto.dna.repository.DnaRepository;
import com.magneto.dna.repository.StatRepository;
import com.magneto.dna.util.MutantDetectorUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MutantDetectorServiceTest {

    final static String[] SAMPLE_MUTANT_DNA =  {"AAAA", "AAAA", "AAAA", "AAAA"};
    final static String[] SAMPLE_HUMAN_DNA =  {"ACTG", "CTGA", "AAGT", "TTGC"};
    final static String[] SAMPLE_INVALID_EMPTY_DNA =  {};
    final static String[] SAMPLE_INVALID_NULL_DNA =  null;

    @Mock
    MutantDetectorUtil mutantDetectorUtilMock;
    @Mock
    DnaRepository dnaRepositoryMock;
    @Mock
    StatRepository statRepositoryMock;

    @InjectMocks
    MutantDetectorService mutantDetectorService;

    @Test
    public void TestExistingDnaIsMutantTrue() {
        // Conditions
        var dnaToTestMock = mock(Dna.class);
        when(dnaRepositoryMock.getById(any())).thenReturn(dnaToTestMock);
        when(dnaToTestMock.isMutant()).thenReturn(true);
        when(dnaToTestMock.getDnaSequence()).thenReturn(SAMPLE_MUTANT_DNA);

        // Test
        try {
            var isMutant = mutantDetectorService.isMutant(dnaToTestMock.getDnaSequence());

            // Verifies
            assert (isMutant == true);
        } catch (InvalidDnaException e) {
            assert (false);
        }
    }

    @Test
    public void TestExistingDnaIsMutantFalse() {
        // Conditions
        var dnaToTestMock = mock(Dna.class);
        when(dnaRepositoryMock.getById(any())).thenReturn(dnaToTestMock);
        when(dnaToTestMock.isMutant()).thenReturn(false);
        when(dnaToTestMock.getDnaSequence()).thenReturn(SAMPLE_HUMAN_DNA);

        // Test
        try {
            var isMutant = mutantDetectorService.isMutant(dnaToTestMock.getDnaSequence());

            // Verifies
            assert (isMutant == false);
        } catch (InvalidDnaException e) {
            assert (false);
        }
    }

    @Test
    public void TestNonExistingDnaIsMutantTrue() {
        // Conditions
        when(dnaRepositoryMock.getById(any())).thenReturn(null);
        when(dnaRepositoryMock.save(any())).thenReturn(true);
        when(mutantDetectorUtilMock.isMutant(SAMPLE_MUTANT_DNA)).thenReturn(true);

        // Test
        try {
            var isMutant = mutantDetectorService.isMutant(SAMPLE_MUTANT_DNA);

            // verifies
            assert (isMutant == true);
        } catch (InvalidDnaException e) {
            assert (false);
        }
    }

    @Test
    public void TestNonExistingDnaIsMutantFalse() {
        // Conditions
        when(dnaRepositoryMock.getById(any())).thenReturn(null);
        when(dnaRepositoryMock.save(any())).thenReturn(true);
        when(mutantDetectorUtilMock.isMutant(SAMPLE_MUTANT_DNA)).thenReturn(false);

        // Test
        try {
            var isMutant = mutantDetectorService.isMutant(SAMPLE_MUTANT_DNA);

            // verifies
            assert (isMutant == false);
        } catch (InvalidDnaException e) {
            assert (false);
        }
    }

    @Test
    public void TestNonExistingDnaIsMutantNoSaved() {
        // Conditions
        when(dnaRepositoryMock.getById(any())).thenReturn(null);
        when(dnaRepositoryMock.save(any())).thenReturn(false);
        when(mutantDetectorUtilMock.isMutant(SAMPLE_MUTANT_DNA)).thenReturn(true);
        // Test
        try {
            var isMutant = mutantDetectorService.isMutant(SAMPLE_MUTANT_DNA);

            // verifies
            assert (isMutant == true);
        } catch (InvalidDnaException e) {
            assert (false);
        }
    }

    @Test
    public void TestEmptyDnaThrowsInvalidDnaException() {

        // Test
        try {
            mutantDetectorService.isMutant(SAMPLE_INVALID_EMPTY_DNA);
            assert (false);
        } catch (InvalidDnaException e) {
            // verifies
            assert (true);
        }
    }

    @Test
    public void TestNullDnaThrowsInvalidDnaException() {

        // Test
        try {
            mutantDetectorService.isMutant(SAMPLE_INVALID_NULL_DNA);
            assert (false);
        } catch (InvalidDnaException e) {
            // verifies
            assert (true);
        }
    }
}
