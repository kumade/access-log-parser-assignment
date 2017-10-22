package com.ef.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BlockedIpAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String ipAddress;

    private LocalDateTime date;

    @Column(length = 1000)
    private String reason;

    public BlockedIpAddress() {
    }

    public BlockedIpAddress(String ipAddress, LocalDateTime date, String reason) {
        this.ipAddress = ipAddress;
        this.date = date;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
