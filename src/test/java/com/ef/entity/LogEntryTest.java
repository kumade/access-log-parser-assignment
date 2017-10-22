package com.ef.entity;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class LogEntryTest {
    @Test
    public void parseValidLogEntry() {
        String[] validLogEntryStringParts = {
            "2017-01-01 00:00:23.003",
            "192.168.169.194",
            "\"GET / HTTP/1.1\"",
            "200",
            "\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36 Edge/14.14393\""
        };

        LogEntry parsedLogEntry = LogEntry.parse(String.join(LogEntry.LOG_ENTRY_LINE_DELIMITER, validLogEntryStringParts));

        Assert.assertEquals(parsedLogEntry.getDate(), LocalDateTime.of(2017, 1, 1, 0, 0, 23, 3 * 1000000));

        Assert.assertEquals(parsedLogEntry.getIpAddress(), validLogEntryStringParts[1]);

        Assert.assertEquals(parsedLogEntry.getMethod(), validLogEntryStringParts[2]);

        Assert.assertEquals(parsedLogEntry.getResponseCode(), (Integer)200);

        Assert.assertEquals(parsedLogEntry.getUserAgent(), validLogEntryStringParts[4]);

    }
}
