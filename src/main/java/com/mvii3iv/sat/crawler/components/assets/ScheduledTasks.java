package com.mvii3iv.sat.crawler.components.assets;

import com.mvii3iv.sat.crawler.components.bills.BillsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ScheduledTasks {

    @Autowired
    private BillsService billsService;

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public ScheduledTasks(BillsService billsService) {
        this.billsService = billsService;
    }

    //@Scheduled(cron = "0 40 6 * * *")

    //@Scheduled(cron = "0 * * * * *")
    //                 s  m h  d m a
    @Scheduled(cron = "0 0 6 * * *")
    public void scheduleTaskWithFixedRate() {
        logger.info("Extraction Task Started - ", dateTimeFormatter.format(LocalDateTime.now()) );
        billsService.extractAllUserDataFromSat();
    }
}
