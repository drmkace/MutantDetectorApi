package com.magneto.dna.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatResponseDto {
    private long humanCount;
    private long mutantCount;
    private float ratio;

    public StatResponseDto(long humanCount, long mutantCount) {
        this.humanCount = humanCount;
        this.mutantCount = mutantCount;
        this.ratio = this.humanCount != 0 ? (float)this.mutantCount / (float)this.humanCount : this.mutantCount;
    }

    @JsonProperty("count_human_dna")
    public long getHumanCount() {
        return humanCount;
    }

    @JsonProperty("count_mutant_dna")
    public long getMutantCount() {
        return mutantCount;
    }

    @JsonProperty("ratio")
    public float getRatio() {
        return ratio;
    }
}
