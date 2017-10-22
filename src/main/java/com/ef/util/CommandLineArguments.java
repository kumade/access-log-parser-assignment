package com.ef.util;

import com.ef.enums.CommandLineArgumentEnum;
import com.ef.enums.DurationEnum;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommandLineArguments {
    public static final DateTimeFormatter START_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss");

    private LocalDateTime startDate;
    private Duration duration;
    private Integer  threshold;
    private String logFileName;

    private CommandLineArguments(LocalDateTime startDate, Duration duration, Integer threshold, String logFileName) {
        this.startDate = startDate;
        this.duration = duration;
        this.threshold = threshold;
        this.logFileName = logFileName;
    }


    public LocalDateTime getStartDate() {
        return startDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public int getThreshold() {
        return threshold;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public static CommandLineArguments parse(String[] args){
        if(args == null || args.length == 0) {
            throw new IllegalArgumentException("No arguments!\r\n");
        }

        LocalDateTime startDate = null;
        Duration duration = null;
        Integer  threshold = null;
        String logFileName = null;

        for(String arg : args) {
            if(arg.startsWith(CommandLineArgumentEnum.START_DATE.toString())) {
                startDate = CommandLineArguments.parseStartDate(arg);
            } else if(arg.startsWith(CommandLineArgumentEnum.DURATION.toString())) {
                duration = CommandLineArguments.parseDuration(arg);
            } else if(arg.startsWith(CommandLineArgumentEnum.THRESHOLD.toString())) {
                threshold = CommandLineArguments.parseThreshold(arg);
            } else if(arg.startsWith(CommandLineArgumentEnum.LOG_FILE.toString())) {
                logFileName = CommandLineArguments.parseLogFileName(arg);
            } else {
                throw new IllegalArgumentException(arg + ": Unknown argument\r\n");
            }
        }

        return new CommandLineArguments(startDate, duration, threshold, logFileName);
    }

    public static LocalDateTime parseStartDate(String startDateString){
        try {
            startDateString = getParameterValue(startDateString, CommandLineArgumentEnum.START_DATE);

            return LocalDateTime.parse(startDateString, START_DATE_FORMATTER);
        } catch(Exception e){
            throw new IllegalArgumentException(CommandLineArgumentEnum.START_DATE + ": empty or invalid parameter!\r\n");
        }
    }

    public static Duration parseDuration(String durationString){
        try {
            durationString = getParameterValue(durationString, CommandLineArgumentEnum.DURATION);

            return DurationEnum.ofString(durationString).toDuration();
        } catch(Exception e){
            throw new IllegalArgumentException(CommandLineArgumentEnum.DURATION + ": empty or invalid parameter!\r\n");
        }
    }

    public static int parseThreshold(String thresholdString) {
        try {
            thresholdString = getParameterValue(thresholdString, CommandLineArgumentEnum.THRESHOLD);

            return Integer.parseInt(thresholdString);
        } catch(Exception e){
            throw new IllegalArgumentException(CommandLineArgumentEnum.THRESHOLD + ": empty or invalid parameter!\r\n");
        }

    }

    public static String parseLogFileName(String logFileNameString){
        try {
            return getParameterValue(logFileNameString, CommandLineArgumentEnum.LOG_FILE);
        } catch(Exception e){
            throw new IllegalArgumentException(CommandLineArgumentEnum.LOG_FILE + ": empty or invalid parameter!\r\n");
        }
    }

    public boolean isIpAddressCheckArgumentsPresent() {
        return startDate != null && duration != null && threshold != null;
    }

    private static String getParameterValue(String keyValuePair, CommandLineArgumentEnum argumentType) {
        if(keyValuePair == null || !keyValuePair.startsWith(argumentType.toString())) {
            throw new IllegalArgumentException();
        }

        return keyValuePair.substring(argumentType.toString().length());

    }
}
