package com.magneto.dna.controller;

import com.magneto.dna.config.DbSchemaBuilder;
import com.magneto.dna.dto.DnaSampleRequestDto;
import com.magneto.dna.exception.InvalidDnaException;
import com.magneto.dna.service.MutantDetectorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MutantDetectorControllerTest {

    @Mock
    DbSchemaBuilder dbSchemaBuilderMock;

    @Mock
    MutantDetectorService mutantDetectorServiceMock;

    @InjectMocks
    MutantDetectorController mutantDetectorController;

    private final static String[] MUTANT_DNA_SAMPLE = new String[] {"AAAA", "AAAA", "AAAA", "AAAA" };
    private final static String[] HUMAN_DNA_SAMPLE = new String[] {"ACGT", "AGCT", "TCGA", "TCGA" };
    private final static String[] INVALID_DNA_SAMPLE = new String[] {"----", "----", "----", "----" };

    @Test
    public void detectMutantReturnStatusOk() throws InvalidDnaException {
        // Conditions
        var request = new MockHttpServletRequest();
        var dnaSampleRequestDto = new DnaSampleRequestDto();
        dnaSampleRequestDto.setDna((MUTANT_DNA_SAMPLE));

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(mutantDetectorServiceMock.isMutant(MUTANT_DNA_SAMPLE)).thenReturn(true);

        // Test
        var response = mutantDetectorController.detectMutant(dnaSampleRequestDto);

        // Verify
        assert (response.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void detectMutantReturnsStatusForbidden() throws InvalidDnaException {
        // Conditions
        var request = new MockHttpServletRequest();
        var dnaSampleRequestDto = new DnaSampleRequestDto();
        dnaSampleRequestDto.setDna((HUMAN_DNA_SAMPLE));

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(mutantDetectorServiceMock.isMutant(HUMAN_DNA_SAMPLE)).thenReturn(false);

        // Test
        var response = mutantDetectorController.detectMutant(dnaSampleRequestDto);

        // Verify
        assert (response.getStatusCode() == HttpStatus.FORBIDDEN);
    }

    @Test
    public void detectMutantInvalidDnaReturnsStatusBadRequest() throws InvalidDnaException {
        // Conditions
        var request = new MockHttpServletRequest();
        var dnaSampleRequestDto = new DnaSampleRequestDto();
        dnaSampleRequestDto.setDna((INVALID_DNA_SAMPLE));

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(mutantDetectorServiceMock.isMutant(INVALID_DNA_SAMPLE)).thenThrow(InvalidDnaException.class);

        // Test
        var response = mutantDetectorController.detectMutant(dnaSampleRequestDto);

        // Verify
        assert (response.getStatusCode() == HttpStatus.BAD_REQUEST);
    }

    @Test
    public void detectMutantNullDnaReturnsStatusBadRequest() {
        // Test
        var response = mutantDetectorController.detectMutant(null);

        // Verify
        assert (response.getStatusCode() == HttpStatus.BAD_REQUEST);
    }

    @Test
    public void pingReturnsOk() {
        // Conditions
        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(dbSchemaBuilderMock.ping()).thenReturn(true);

        // Test
        var response = mutantDetectorController.ping();

        // Verify
        assert (response.getDataBaseStatus().equals("OK"));
        assert (response.getRestApiStatus().equals("OK"));
    }

    @Test
    public void pingReturnsDatabaseNA() {
        // Conditions
        var request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(dbSchemaBuilderMock.ping()).thenReturn(false);

        // Test
        var response = mutantDetectorController.ping();

        // Verify
        assert (response.getDataBaseStatus().equals("N/A"));
        assert (response.getRestApiStatus().equals("OK"));
    }

}
