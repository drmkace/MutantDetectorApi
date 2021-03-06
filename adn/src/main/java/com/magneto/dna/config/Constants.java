package com.magneto.dna.config;

public final class Constants {
    // Table Names
    public static final String DNA_TABLE_NAME = "Dna";

    // Table Fields
    public static final String DNA_TABLE_KEY = "Id";
    public static final String DNA_TABLE_DNA_SAMPLE = "DnaSample";
    public static final String DNA_TABLE_IS_MUTANT = "IsMutant";
    public static final String STAT_HUMAN_COUNT = "HumanCount";
    public static final String STAT_MUTANT_COUNT = "MutantCount";

    public static final String CONF_DYNAMODB_REGION = "dynamodb.region";
    public static final String CONF_REDIS_HOST = "redis.host";
    public static final String CONF_REDIS_PORT = "redis.port";
}
