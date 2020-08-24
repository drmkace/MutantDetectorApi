package com.magneto.dna.repository;

import com.magneto.dna.entity.Stat;
import com.magneto.dna.config.Constants;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class StatRepository {

    private static final HashMap<String, Long> cache = new HashMap<>();

    public void IncrementHumanCount() {
        try {
            synchronized(cache) {
                if(cache.containsKey(Constants.STAT_HUMAN_COUNT)) {
                    var cachedCount= cache.get(Constants.STAT_HUMAN_COUNT);
                    cachedCount++;
                    cache.put(Constants.STAT_HUMAN_COUNT, cachedCount);
                }
                else
                {
                    cache.put(Constants.STAT_HUMAN_COUNT, 1L);
                }
            }
        } catch(Exception ex) {
            // TODO: Log Exception
        }
    }

    public void IncrementMutantCount() {
        try {
            synchronized(cache) {
                if(cache.containsKey(Constants.STAT_MUTANT_COUNT)) {
                    var cachedCount= cache.get(Constants.STAT_MUTANT_COUNT);
                    cachedCount++;
                    cache.put(Constants.STAT_MUTANT_COUNT, cachedCount);
                }
                else
                {
                    cache.put(Constants.STAT_MUTANT_COUNT, 1L);
                }
            }
        } catch(Exception ex) {
            // TODO: Log Exception
        }
    }

    public void save(Stat entity) {
        try {
            synchronized(cache) {
                cache.put(Constants.STAT_MUTANT_COUNT, entity.getMutantCount());
                cache.put(Constants.STAT_HUMAN_COUNT, entity.getHumanCount());
            }
        } catch(Exception ex) {
            // TODO: Log Exception
        }
    }

    public Stat tryGet() {
        try {
            synchronized(cache) {
                return new Stat(
                        cache.get(Constants.STAT_HUMAN_COUNT),
                        cache.get(Constants.STAT_MUTANT_COUNT));
            }
        } catch(Exception ex) {
            // TODO: Log Exception
            return null;
        }
    }
}