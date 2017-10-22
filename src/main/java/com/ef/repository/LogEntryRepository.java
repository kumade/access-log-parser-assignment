package com.ef.repository;

import com.ef.entity.LogEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LogEntryRepository extends CrudRepository<LogEntry, Long>{

    @Query("select logEntry.ipAddress, count(*) from LogEntry logEntry where logEntry.date between ?1 and ?2 group by logEntry.ipAddress having count(*) > ?3")
    List<Object[]> findByDatePeriodAndCountGreaterThanThreshold(LocalDateTime dateFrom, LocalDateTime dateTo, long threshold);


}
