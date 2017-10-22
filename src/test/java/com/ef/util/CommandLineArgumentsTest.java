package com.ef.util;


import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

public class CommandLineArgumentsTest {

    @Test
    public void parseValidStartDateParameter() {
        String validStartDateString = "--startDate=2017-01-01.13:00:00";
        LocalDateTime expectedStartDate = LocalDateTime.parse("2017-01-01.13:00:00", CommandLineArguments.START_DATE_FORMATTER);


        Assert.assertEquals(expectedStartDate, CommandLineArguments.parseStartDate(validStartDateString));
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseInvalidStartDateParameterName() {
        String invalidStartDateString = "--startAt=2017-01-01.13:00:00";

        CommandLineArguments.parseStartDate(invalidStartDateString);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseInvalidStartDateParameterValue() {
        String invalidStartDateString = "--startDate=";

        CommandLineArguments.parseStartDate(invalidStartDateString);
    }

    @Test
    public void parseValidDurationParameter() {
        String validDurationString = "--duration=hourly";
        Duration expectedDuration = Duration.ofHours(1);

        Assert.assertEquals(expectedDuration, CommandLineArguments.parseDuration(validDurationString));

        validDurationString = "--duration=daily";
        expectedDuration = Duration.ofDays(1);

        Assert.assertEquals(expectedDuration, CommandLineArguments.parseDuration(validDurationString));
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseInvalidDurationParameterName() {
        String invalidDurationString = "--period=hourly";

        CommandLineArguments.parseDuration(invalidDurationString);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseInvalidDurationParameterValue() {
        String invalidDurationString = "--duration=";

        CommandLineArguments.parseDuration(invalidDurationString);
    }

    @Test
    public void parseValidThresholdParameter() {
        String validThresholdString = "--threshold=200";
        int expectedThreshold = 200;

        Assert.assertEquals(expectedThreshold, CommandLineArguments.parseThreshold(validThresholdString));

        validThresholdString = "--threshold=500";
        expectedThreshold = 500;

        Assert.assertEquals(expectedThreshold, CommandLineArguments.parseThreshold(validThresholdString));
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseInvalidThresholdParameterName() {
        String invalidThresholdString = "--trashhold=200";

        CommandLineArguments.parseThreshold(invalidThresholdString);

    }

    @Test(expected = IllegalArgumentException.class)
    public void parseInvalidThresholdParameterValue() {
        String invalidThresholdString = "--threshold=hundred";

        CommandLineArguments.parseThreshold(invalidThresholdString);

    }

    @Test
     public void parseAccessLogRequestParameters() {
        String[] parameters = new String[] {"--startDate=2017-01-01.13:12:11", "--duration=hourly",  "--threshold=150"};

        CommandLineArguments parsedArguments = CommandLineArguments.parse(parameters);

        Assert.assertEquals(parsedArguments.getStartDate(), LocalDateTime.of(2017, 1, 1, 13, 12, 11));

        Assert.assertEquals(parsedArguments.getDuration(), Duration.ofHours(1));

        Assert.assertEquals(parsedArguments.getThreshold(), 150);
    }

    @Test
    public void parseAccessLogFileNameParameter() {
        String[] parameters = new String[] {"--accesslog=access.log"};

        CommandLineArguments parsedArguments = CommandLineArguments.parse(parameters);

        Assert.assertEquals(parsedArguments.getLogFileName(), "access.log");
    }

}
