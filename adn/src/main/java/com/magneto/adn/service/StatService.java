package com.magneto.adn.service;

import com.magneto.adn.entity.Stat;
import com.magneto.adn.repository.StatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatService {
    private StatRepository repository;

    @Autowired
    public StatService(StatRepository repository) {
        this.repository = repository;
    }

    public void saveOrUpdate(Stat entity) {
        this.repository.saveOrUdate(entity);
    }

    public Stat get() {
        return this.repository.get();
    }
}
