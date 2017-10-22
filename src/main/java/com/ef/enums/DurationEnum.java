package com.ef.enums;

import java.time.Duration;

public enum DurationEnum {

    HOURLY("hourly"),
    DAILY("daily");

    private final String value;

    private DurationEnum(final String value) {
        this.value = value;
    }

    public static DurationEnum ofString(String value) {
        switch (value) {
            case "hourly" : return HOURLY;
            case "daily" : return DAILY;
            default: throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return value;
    }

    public Duration toDuration() {
        if(this.equals(HOURLY)) {
            return Duration.ofHours(1);
        } else {
            return Duration.ofDays(1);
        }
    }
}
