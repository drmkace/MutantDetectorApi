package com.magneto.dna.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MutantDetectorUtil {
    private final static Pattern MUTANT_PATTERN = Pattern.compile("A{4}|T{4}|C{4}|G{4}");
    private final static int MINIMUM_MUTANT_MATCH_COUNT = 2;
    private final static int MATCH_LENGTH = 4;

    private static final Logger logger = LoggerFactory.getLogger(MutantDetectorUtil.class);

    public boolean isMutant(String[] dna) {
        var matchCount = this.getHorizontalMatchCount(dna);
        if (!this.isVerificationCompleted(matchCount)) {
            matchCount += this.getVerticalMatchCount(dna, matchCount);
            if (!isVerificationCompleted(matchCount)) {
                matchCount += this.getCrossMatchCount(dna, matchCount);
            }
        }
        return isVerificationCompleted(matchCount);
    }

    private long getHorizontalMatchCount(String[] dna) {
        logger.debug("-- HORIZONTAL --");
        var matchCount = 0;
        for (var i = 0; i < dna.length && !this.isVerificationCompleted(matchCount); i++) {
            matchCount += this.getMatchCount(dna[i]);
        }
        return matchCount;
    }

    private long getVerticalMatchCount(String[] dna, long currentMatchCount) {
        logger.debug("-- VERTICAL --");
        long matchCount = 0;
        for (var colIndex = 0; colIndex < dna.length && !this.isVerificationCompleted(matchCount + currentMatchCount); colIndex++) {
            var builder = new StringBuilder();
            for (var rowIndex = 0; rowIndex < dna[colIndex].length(); rowIndex++) {
                builder.append(dna[rowIndex].charAt(colIndex));
            }
            var dnaColumn = builder.toString();
            matchCount += this.getMatchCount(dnaColumn);
        }
        return matchCount;
    }

    private long getCrossMatchCount(String[] dna, long currentMatchCount) {
        long matchCount = 0;
        int colIndex = 0;
        matchCount += this.getLeftToRightFromTopMatchCount(dna, colIndex, currentMatchCount);
        if (!this.isVerificationCompleted(currentMatchCount + matchCount)) {
            matchCount += this.getLeftToRightFromBottomMatchCount(dna, colIndex,currentMatchCount + matchCount);
        }
        return matchCount;
    }

    private long getLeftToRightFromTopMatchCount(String[] dna, int firstColIndex, long currentMatchCount) {
        logger.debug("-- LEFT TO RIGHT TOP --");
        long matchCount = 0;
        var maxRowIndex = dna.length - (MATCH_LENGTH - 1);
        for (var rowIndex = 0; rowIndex < maxRowIndex && !this.isVerificationCompleted(matchCount + currentMatchCount); rowIndex++) {
            var builder = new StringBuilder();
            var newRowIndex = rowIndex;
            for (var colIndex = firstColIndex; colIndex < dna.length && newRowIndex < dna.length; colIndex++) {
                builder.append(dna[newRowIndex].charAt(colIndex));
                newRowIndex++;
            }
            var dnaDiagonal = builder.toString();
            matchCount += this.getMatchCount(dnaDiagonal);
        }
        return matchCount;
    }

    private long getLeftToRightFromBottomMatchCount(String[] dna, int firstColIndex, long currentMatchCount) {
        logger.debug("-- LEFT TO RIGHT BOTTOM --");
        long matchCount = 0;
        var minRowIndex = MATCH_LENGTH - 1;
        for (var rowIndex = dna.length - 1; rowIndex >= minRowIndex && !this.isVerificationCompleted(matchCount + currentMatchCount); rowIndex--) {
            var builder = new StringBuilder();
            var newRowIndex = rowIndex;
            for (var colIndex = firstColIndex; colIndex < dna.length && newRowIndex >= 0; colIndex++) {
                builder.append(dna[newRowIndex].charAt(colIndex));
                newRowIndex--;
            }
            var dnaDiagonal = builder.toString();
            matchCount += this.getMatchCount(dnaDiagonal);
        }
        return matchCount;
    }

    private long getMatchCount(String dnaSample) {
        Matcher countMatcher = MUTANT_PATTERN.matcher(dnaSample);
        long matchCount = countMatcher.results().count();
        logger.debug(dnaSample + (matchCount > 0 ? " - MATCH" : ""));
        return matchCount;
    }

    private boolean isVerificationCompleted(long currentMatchCount) {
        return currentMatchCount >= MINIMUM_MUTANT_MATCH_COUNT;
    }
}