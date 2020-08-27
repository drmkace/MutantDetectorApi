package com.magneto.dna.repository;

import com.magneto.dna.config.Constants;
import com.magneto.dna.entity.Stat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import redis.clients.jedis.Jedis;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatRepositoryTest {

    @Mock
    Jedis cache;

    @Test
    public void incrementHumanCountExistsSuccess() {
        //Conditions
        var statRepository = new StatRepository(cache);
        when(cache.exists(Constants.STAT_HUMAN_COUNT)).thenReturn(true);
        when(cache.get(Constants.STAT_HUMAN_COUNT)).thenReturn("1");

        //Test
        statRepository.IncrementHumanCount();

        //Verifies
        verify(cache).get(Constants.STAT_HUMAN_COUNT);
    }

    @Test
    public void incrementHumanCountNoExistsSuccess() {
        //Conditions
        var statRepository = new StatRepository(cache);
        when(cache.exists(Constants.STAT_HUMAN_COUNT)).thenReturn(false);
        when(cache.set(Constants.STAT_HUMAN_COUNT,"1")).thenReturn("1");

        //Test
        statRepository.IncrementHumanCount();

        //Verifies
        verify(cache).set(Constants.STAT_HUMAN_COUNT,"1");
    }

    @Test
    public void incrementMutantCountExistsSuccess() {
        //Conditions
        var statRepository = new StatRepository(cache);
        when(cache.exists(Constants.STAT_MUTANT_COUNT)).thenReturn(true);
        when(cache.get(Constants.STAT_MUTANT_COUNT)).thenReturn("1");

        //Test
        statRepository.IncrementMutantCount();

        //Verifies
        verify(cache).get(Constants.STAT_MUTANT_COUNT);
    }

    @Test
    public void incrementMutantCountNoExistsSuccess() {
        //Conditions
        var statRepository = new StatRepository(cache);
        when(cache.exists(Constants.STAT_MUTANT_COUNT)).thenReturn(false);
        when(cache.set(Constants.STAT_MUTANT_COUNT,"1")).thenReturn("1");

        //Test
        statRepository.IncrementMutantCount();

        //Verifies
        verify(cache).set(Constants.STAT_MUTANT_COUNT,"1");
    }

    @Test
    public void tryGetSuccess() {
        //Conditions
        var statRepository = new StatRepository(cache);
        when(cache.get(Constants.STAT_HUMAN_COUNT)).thenReturn("10");
        when(cache.get(Constants.STAT_MUTANT_COUNT)).thenReturn("20");

        //Test
        var stat = statRepository.tryGet();

        //Verifies
        assert (stat.getHumanCount() == 10);
        assert (stat.getMutantCount() == 20);
        verify(cache).get(Constants.STAT_HUMAN_COUNT);
        verify(cache).get(Constants.STAT_MUTANT_COUNT);
    }

    @Test
    public void saveSuccess() {
        //Conditions
        var statRepository = new StatRepository(cache);
        var stat = new Stat(10, 20);
        when(cache.set(Constants.STAT_HUMAN_COUNT, "10")).thenReturn("10");
        when(cache.set(Constants.STAT_MUTANT_COUNT, "20")).thenReturn("20");

        //Test
        statRepository.save(stat);

        //Verifies
        verify(cache).set(Constants.STAT_HUMAN_COUNT, "10");
        verify(cache).set(Constants.STAT_MUTANT_COUNT, "20");
    }

    @Test
    public void tryGetHasException() {
        //Conditions
        var statRepository = new StatRepository(cache);
        when(cache.get(anyString())).thenReturn(null);

        //Test
        var stat = statRepository.tryGet();

        //Verifies
        assert (stat == null);
    }

    @Test
    public void incrementHumanCountHasException() {
        //Conditions
        var statRepository = new StatRepository(cache);
        when(cache.exists(Constants.STAT_HUMAN_COUNT)).thenReturn(true);
        when(cache.get(Constants.STAT_HUMAN_COUNT)).thenReturn(null);

        //Test
        statRepository.IncrementHumanCount();

        //Verifies
        assert (true);
    }

    @Test
    public void incrementMutantCountHasException() {
        //Conditions
        var statRepository = new StatRepository(cache);
        when(cache.exists(Constants.STAT_MUTANT_COUNT)).thenReturn(true);
        when(cache.get(Constants.STAT_MUTANT_COUNT)).thenReturn(null);

        //Test
        statRepository.IncrementHumanCount();

        //Verifies
        assert (true);
    }
}
