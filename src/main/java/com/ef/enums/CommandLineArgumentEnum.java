package com.ef.enums;

public enum CommandLineArgumentEnum {
    START_DATE("--startDate="),
    DURATION("--duration="),
    THRESHOLD("--threshold="),
    LOG_FILE("--accesslog=");

    private final String prefix;

    private CommandLineArgumentEnum(final String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return prefix;
    }
}
