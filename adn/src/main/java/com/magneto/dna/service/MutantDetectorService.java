package com.magneto.dna.service;

import com.magneto.dna.entity.Dna;
import com.magneto.dna.exception.InvalidDnaException;
import com.magneto.dna.repository.DnaRepository;
import com.magneto.dna.repository.StatRepository;
import com.magneto.dna.util.MutantDetectorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MutantDetectorService {
    private static final Logger logger = LoggerFactory.getLogger(MutantDetectorService.class);

    private final MutantDetectorUtil mutantDetectorUtil;
    private final DnaRepository dnaRepository;
    private final StatRepository statRepository;

    @Autowired
    public MutantDetectorService(
            MutantDetectorUtil mutantDetectorUtil,
            DnaRepository dnaRepository,
            StatRepository statRepository) {
        this.mutantDetectorUtil = mutantDetectorUtil;
        this.dnaRepository = dnaRepository;
        this.statRepository = statRepository;
    }

    public boolean isMutant(String[] dnaSample) throws InvalidDnaException {
        this.validateDna(dnaSample);
        var isMutant = false;
        var dnaToFind = new Dna(dnaSample);
        var dnaEntity = this.dnaRepository.getById(dnaToFind.getId());

        if(dnaEntity == null) {
            isMutant = this.mutantDetectorUtil.isMutant(dnaSample);

            var newDnaEntity = new Dna(dnaSample, isMutant);
            var dnaSaved = dnaRepository.save(newDnaEntity);
            if(dnaSaved) {
                if(isMutant) {
                    this.statRepository.IncrementMutantCount();
                }
                else {
                    this.statRepository.IncrementHumanCount();
                }
            }
            else
            {
                logger.error("The Dna Entity could not be saved");
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
