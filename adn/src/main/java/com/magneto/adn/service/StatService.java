package com.magneto.adn.service;

import com.magneto.adn.entity.Stat;
import com.magneto.adn.repository.DnaRepository;
import com.magneto.adn.repository.StatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatService {
    private StatRepository statRepository;
    private DnaRepository dnaRepository;

    @Autowired
    public StatService(StatRepository statRepository, DnaRepository dnaRepository) {
        this.statRepository = statRepository;
        this.dnaRepository = dnaRepository;
    }


    public void saveOrUpdate(Stat entity) {
        this.statRepository.saveOrUdate(entity);
    }

    public Stat get() {
        var humanCount =  dnaRepository.getHumanCount();
        var mutantCount =  dnaRepository.getMutantCount();
        return new Stat(humanCount, mutantCount);
    }
}
