package com.magneto.adn.service;

import com.magneto.adn.entity.Stat;
import com.magneto.adn.repository.DnaRepository;
import com.magneto.adn.repository.StatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatService {
    private final StatRepository statRepository;
    private final DnaRepository dnaRepository;

    @Autowired
    public StatService(StatRepository statRepository, DnaRepository dnaRepository) {
        this.statRepository = statRepository;
        this.dnaRepository = dnaRepository;
    }

    public Stat get() {
        var stat = statRepository.tryGet();
        if(stat != null) {
            return stat;
        } else {
            var humanCount =  dnaRepository.getHumanCount();
            var mutantCount =  dnaRepository.getMutantCount();
            stat = new Stat(humanCount, mutantCount);

            this.statRepository.save(stat);
            return  stat;
        }
    }
}
