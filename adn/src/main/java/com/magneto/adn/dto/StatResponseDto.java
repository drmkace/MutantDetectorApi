package com.magneto.adn.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatResponseDto {
    private long humanCount;
    private long mutantCount;
    private float ratio;

    public StatResponseDto(long humanCount, long mutantCount) {
        this.humanCount = humanCount;
        this.mutantCount = mutantCount;
        this.ratio = (float)this.mutantCount / (float)this.humanCount ;
    }

    @JsonProperty("count_human_dna")
    public long getHumanCount() {
        return humanCount;
    }

    public void setHumanCount(long humanCount) {
        this.humanCount = humanCount;
    }

    @JsonProperty("count_mutant_dna")
    public long getMutantCount() {
        return mutantCount;
    }

    public void setMutantCount(long mutantCount) {
        this.mutantCount = mutantCount;
    }

    @JsonProperty("ration")
    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }
}
