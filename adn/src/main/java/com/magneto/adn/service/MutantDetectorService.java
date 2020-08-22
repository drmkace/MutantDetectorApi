package com.magneto.adn.service;

import com.magneto.adn.entity.Dna;
import com.magneto.adn.exception.InvalidDnaException;
import com.magneto.adn.repository.DnaRepository;
import com.magneto.adn.repository.StatRepository;
import com.magneto.adn.util.MutantDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MutantDetectorService {
    private MutantDetector mutantDetector;
    private DnaRepository dnaRepository;
    private StatRepository statRepository;

    @Autowired
    public MutantDetectorService(
            MutantDetector mutantDetector,
            DnaRepository dnaRepository,
            StatRepository statRepository) {
        this.mutantDetector = mutantDetector;
        this.dnaRepository = dnaRepository;
        this.statRepository = statRepository;
    }

    public boolean isMutant(String[] dnaSample) throws InvalidDnaException {
        this.validateDna(dnaSample);
        var isMutant = false;
        var dnaToFind = new Dna(dnaSample);
        var dnaEntity = this.dnaRepository.getById(dnaToFind.getId());

        if(dnaEntity == null) {
            isMutant = this.mutantDetector.isMutant(dnaSample);
            var stat = statRepository.get();
            if(isMutant) {
                stat.incrementMutantCount();
            }
            else {
                stat.incrementHumanCount();
            }

            var newDnaEntity = new Dna(dnaSample, isMutant);
            var dnaSaved = dnaRepository.save(newDnaEntity);
            if(dnaSaved) {
                statRepository.saveOrUdate(stat);
            }
            else
            {
                //TODO: Throw Exception
            }
        } else {
            isMutant = dnaEntity.isMutant();
        }
        return isMutant;
    }

    private void validateDna(String[] dna) throws InvalidDnaException {
        if (dna == null || dna.length == 0) {
            throw new InvalidDnaException();
        }
    }
}
