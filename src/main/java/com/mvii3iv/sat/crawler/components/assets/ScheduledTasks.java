package com.mvii3iv.sat.crawler.components.assets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleTaskWithFixedRate() {
        logger.info("Cron :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
    }
}
