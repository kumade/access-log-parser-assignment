package com.ef.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Entity
public class LogEntry {
    private static final String LOG_ENTRY_LINE_DELIMITER_REGEX = "\\|";

    public static final String LOG_ENTRY_LINE_DELIMITER = "|";
    public static final int LOG_ENTRY_LINE_COLUMNS_NUMBER = 5;
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    @Column(length = 100)
    private String ipAddress;

    @Column(length = 100)
    private String method;

    private Integer responseCode;

    @Column(length = 1000)
    private String userAgent;

    public LogEntry() {
    }

    public LogEntry(LocalDateTime date, String ipAddress, String method, Integer responseCode, String userAgent) {
        this.date = date;
        this.ipAddress = ipAddress;
        this.method = method;
        this.responseCode = responseCode;
        this.userAgent = userAgent;
    }

    public static LogEntry parse(String logEntryLine) {
        String[] values  = null;
        Optional<String> line = Optional.ofNullable(logEntryLine);
        try {
            if(!line.isPresent() || (values = logEntryLine.split(LOG_ENTRY_LINE_DELIMITER_REGEX)).length != LOG_ENTRY_LINE_COLUMNS_NUMBER) {
                System.err.println(values != null ? values.length : 0);
                throw new IllegalArgumentException(" Invalid columns count\r\n");
            }

            return new LogEntry(LocalDateTime.parse(values[0], DATE_FORMATTER), values[1], values[2], Integer.parseInt(values[3]), values[4]);

        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot parse a log entry from a line: " + line.orElse("<empty line>") + e.getMessage());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
