package com.magneto.dna.entity;

import java.util.Arrays;

public class Dna {
    private String[] dnaSequence;
    private boolean isMutant;

    public Dna(String[] dnaSequence, Boolean isMutant) {
        this.dnaSequence = dnaSequence;
        this.isMutant = isMutant;
    }

    public Dna(String... dnaSequence) {
        this(dnaSequence, false);
    }
    public String[] getDnaSequence() {
        return this.dnaSequence;
    }

    public void setDnaSequence(String[] dnaSequence) {

        this.dnaSequence = dnaSequence;
    }

    public boolean isMutant() {

        return this.isMutant;
    }

    public void setMutant(boolean mutant) {
        isMutant = mutant;
    }

    public String getId() {
        int result = 31 * Arrays.hashCode(this.dnaSequence);
        return Integer.toString(result);
    }
}