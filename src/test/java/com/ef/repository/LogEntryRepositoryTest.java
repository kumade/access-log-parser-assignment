package com.ef.repository;

import com.ef.entity.LogEntry;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations="classpath:test.properties")
public class LogEntryRepositoryTest {
    @Autowired
    private LogEntryRepository logEntryRepository;

    @Test
     public void basicOperations() {
        LogEntry logEntry = new LogEntry(LocalDateTime.now(), "127.0.0.1", "GET", 200, "Opera");

        logEntryRepository.save(logEntry);

        List<LogEntry> persistedLogEntries = Lists.newArrayList(logEntryRepository.findAll());

        assertThat(persistedLogEntries.size()).isEqualTo(1);

        persistedLogEntries.get(0).setIpAddress("192.168.1.1");

        logEntryRepository.save(persistedLogEntries.get(0));

        persistedLogEntries = Lists.newArrayList(logEntryRepository.findAll());

        assertThat(persistedLogEntries.size()).isEqualTo(1);

        assertThat(persistedLogEntries.get(0).getIpAddress()).isEqualTo("192.168.1.1");

    }

    @Test
    public void findByDatePeriodAndCountGreaterThanThreshold() {
        LogEntry[] logEntries = {
            new LogEntry(LocalDateTime.of(2017, 1, 1, 12, 0, 0), "127.0.0.1", "GET", 200, "Opera"),
            new LogEntry(LocalDateTime.of(2017, 1, 1, 12, 30, 0), "127.0.0.1", "GET", 200, "Opera"),
            new LogEntry(LocalDateTime.of(2017, 1, 1, 12, 0, 0), "192.168.1.1", "GET", 200, "Opera"),
            new LogEntry(LocalDateTime.of(2017, 1, 1, 13, 30, 0), "192.168.1.1", "GET", 200, "Opera"),
            new LogEntry(LocalDateTime.of(2017, 1, 1, 14, 30, 0), "192.168.1.1", "GET", 200, "Opera")
        };

        logEntryRepository.save(Arrays.asList(logEntries));

        assertThat(logEntryRepository.count()).isEqualTo(5);

        // one hour period
        List<Object[]> persistedLogEntries = logEntryRepository.findByDatePeriodAndCountGreaterThanThreshold(
            LocalDateTime.of(2017, 1, 1, 12, 0, 0),
            LocalDateTime.of(2017, 1, 1, 13, 0, 0),
            1
        );

        assertThat(persistedLogEntries.size()).isEqualTo(1);

        assertThat(persistedLogEntries.get(0)[0]).isEqualTo("127.0.0.1");

        assertThat(persistedLogEntries.get(0)[1]).isEqualTo(2L);

        // one day period
        persistedLogEntries = logEntryRepository.findByDatePeriodAndCountGreaterThanThreshold(
                LocalDateTime.of(2017, 1, 1, 12, 0, 0),
                LocalDateTime.of(2017, 1, 2, 12, 0, 0),
                2
        );

        assertThat(persistedLogEntries.size()).isEqualTo(1);

        assertThat(persistedLogEntries.get(0)[0]).isEqualTo("192.168.1.1");

        assertThat(persistedLogEntries.get(0)[1]).isEqualTo(3L);

        // invalid period
        persistedLogEntries = logEntryRepository.findByDatePeriodAndCountGreaterThanThreshold(
                LocalDateTime.of(2049, 1, 1, 12, 0, 0),
                LocalDateTime.of(2049, 1, 2, 12, 0, 0),
                1
        );

        assertThat(persistedLogEntries.size()).isEqualTo(0);
    }


}
