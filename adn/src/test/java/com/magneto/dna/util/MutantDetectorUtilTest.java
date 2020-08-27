package com.magneto.dna.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MutantDetectorUtilTest {

    @InjectMocks
    MutantDetectorUtil mutantDetectorUtil;

    @Test
    public void DnaWithMutantSequenceInRowsIsMutantTrue() {
        var dnaToTest = new String[] {"AAAATT", "TAGCT", "GATCTT", "CGGAGT", "AACAGA", "TTTTCT"};
        var isMutant = mutantDetectorUtil.isMutant(dnaToTest);
        assert (isMutant);
    }

    @Test
    public void DnaWithMutantSequenceInColsIsMutantTrue() {
        var dnaToTest = new String[] {"A----T", "A----T", "A----T", "A----T", "A----T", "A----T"};
        var isMutant = mutantDetectorUtil.isMutant(dnaToTest);
        assert (isMutant);
    }

    @Test
    public void DnaWithMutantSequenceInRowAndUpDownCrossIsMutantTrue() {
        var dnaToTest = new String[] {"A-----", "-A----", "--A---", "---A--", "------", "AAAAAA"};
        var isMutant = mutantDetectorUtil.isMutant(dnaToTest);
        assert (isMutant);
    }

    @Test
    public void DnaWithMutantSequenceInColAndUpDownCrossIsMutantTrue() {
        var dnaToTest = new String[] {"------", "-A----", "T-A---", "T--A--", "T---A-", "T-----"};
        var isMutant = mutantDetectorUtil.isMutant(dnaToTest);
        assert (isMutant);
    }

    @Test
    public void DnaWithMutantSequenceInColsAndRowsIsMutantTrue() {
        var dnaToTest = new String[] {"------", "-----T", "-----T", "-----T", "-----T", "-AAAA-"};
        var isMutant = mutantDetectorUtil.isMutant(dnaToTest);
        assert (isMutant);
    }

    @Test
    public void DnaWithMutantSequenceInRowAndDownUpCrossIsMutantTrue() {
        var dnaToTest = new String[] {"AAAA--", "------", "---G--", "--G---", "-G----", "G-----"};
        var isMutant = mutantDetectorUtil.isMutant(dnaToTest);
        assert (isMutant);
    }

    @Test
    public void DnaWithMutantSequenceInColAndDownUpCrossIsMutantTrue() {
        var dnaToTest = new String[] {"A-----", "A-----", "A--G--", "A-G---", "-G----", "G-----"};
        var isMutant = mutantDetectorUtil.isMutant(dnaToTest);
        assert (isMutant);
    }

    @Test
    public void DnaWithNoMutantSequenceIsMutantFalse() {
        var dnaToTest = new String[] {"------", "------", "------", "------", "------", "------"};
        var isMutant = mutantDetectorUtil.isMutant(dnaToTest);
        assert (!isMutant);
    }

}
