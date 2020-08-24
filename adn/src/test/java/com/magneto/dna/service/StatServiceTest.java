package com.magneto.dna.service;

import com.magneto.dna.entity.Stat;
import com.magneto.dna.repository.DnaRepository;
import com.magneto.dna.repository.StatRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StatServiceTest {

    @Mock
    StatRepository stateRepositoryMock;

    @Mock
    DnaRepository dnaRepositoryMock;

    @InjectMocks
    StatService statService;

    @Test
    public void GetReturnsDataFromStatRepository() {
        //Conditions
        var mockStat = mock(Stat.class);

        when(stateRepositoryMock.tryGet())
            .thenReturn((mockStat));

        //Test
        var stat = statService.get();

        //Verifies
        assert (stat != null);
        verify(stateRepositoryMock).tryGet();
    }

    @Test
    public void GetReturnsDataFromDnaRepository() {
        //Conditions
        var mockStat = mock(Stat.class);

        when(stateRepositoryMock.tryGet()).thenReturn((null));
        when(dnaRepositoryMock.getHumanCount()).thenReturn(10L);
        when(dnaRepositoryMock.getMutantCount()).thenReturn(10L);

        //Test
        var stat = statService.get();

        //Verifies
        assert (stat != null);
        verify(dnaRepositoryMock).getHumanCount();
        verify(dnaRepositoryMock).getMutantCount();
    }
}
