package com.magneto.dna.repository;

import com.magneto.dna.entity.Stat;
import com.magneto.dna.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@Repository
public class StatRepository {

    private static final Logger logger = LoggerFactory.getLogger(StatRepository.class);

    private Jedis cache;

    public StatRepository() {
        this.cache = new Jedis("localhost");
    }

    public void IncrementHumanCount() {
        try {
            synchronized(this.cache) {
                if(this.cache.exists(Constants.STAT_HUMAN_COUNT)) {
                    var cachedCount= Long.parseLong(cache.get (Constants.STAT_HUMAN_COUNT));
                    cachedCount++;
                    this.cache.set(Constants.STAT_HUMAN_COUNT, Long.toString(cachedCount));
                } else {
                    this.cache.set(Constants.STAT_HUMAN_COUNT, "1");
                }
            }
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void IncrementMutantCount() {
        try {
            synchronized (this.cache) {
                if(this.cache.exists(Constants.STAT_MUTANT_COUNT)) {
                    var cachedCount= Long.parseLong(cache.get (Constants.STAT_MUTANT_COUNT));
                    cachedCount++;
                    this.cache.set(Constants.STAT_MUTANT_COUNT, Long.toString(cachedCount));
                } else {
                    this.cache.set(Constants.STAT_MUTANT_COUNT, "1");
                }
            }
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void save(Stat entity) {
        try {
            synchronized (this.cache) {
                this.cache.set(Constants.STAT_HUMAN_COUNT, Long.toString(entity.getHumanCount()));
                this.cache.set(Constants.STAT_MUTANT_COUNT, Long.toString(entity.getMutantCount()));
            }
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public Stat tryGet() {
        try {
            return new Stat(
                    Long.parseLong(this.cache.get(Constants.STAT_HUMAN_COUNT)),
                    Long.parseLong(this.cache.get(Constants.STAT_MUTANT_COUNT)));
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }
}