package com.magneto.adn.entity;

public class Stat {
    private long humanCount;
    private long mutantCount;

    public Stat(long humanCount, long mutantCount) {
        this.humanCount = humanCount;
        this.mutantCount = mutantCount;
    }

    public long getHumanCount() {
        return humanCount;
    }

    public void setHumanCount(long humanCount) {
        this.humanCount = humanCount;
    }

    public long getMutantCount() {
        return mutantCount;
    }

    public void setMutantCount(long mutantCount) {
        this.mutantCount = mutantCount;
    }

    public void incrementHumanCount() {
        this.humanCount++;
    }

    public void incrementMutantCount() {
        this.mutantCount++;
    }
}
