package com.magneto.dna.controller;

import com.magneto.dna.entity.Stat;
import com.magneto.dna.service.StatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatControllerTest {

    @Mock
    StatService statServiceMock;

    @InjectMocks
    StatController statController;

    @Test
    public void getStatSuccess() {
        // Conditions
        var request = new MockHttpServletRequest();
        var statMock = mock(Stat.class);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(statServiceMock.get()).thenReturn(statMock);

        // Test
        var response = statController.getStat();

        // Verify
        assert (response.getStatusCode() == HttpStatus.OK);
        assert (response.getBody() != null);
    }
}
