package com.magneto.adn.dto;

public class StatResponseDto {
    private long humanCount;
    private long mutantCount;
    private float ratio;

    public StatResponseDto(long humanCount, long mutantCount) {
        this.humanCount = humanCount;
        this.mutantCount = mutantCount;
        this.ratio = (float)this.mutantCount / (float)this.humanCount ;
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

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }
}
