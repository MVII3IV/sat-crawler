package com.mvii3iv.sat.crawler.components.assets;

import com.mvii3iv.sat.crawler.components.bills.BillsService;
import com.mvii3iv.sat.crawler.components.customers.Customers;
import com.mvii3iv.sat.crawler.components.customers.CustomersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ScheduledTasks {

    @Autowired
    private CustomersService customersService;

    @Autowired
    private BillsService billsService;

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public ScheduledTasks(CustomersService customersService, BillsService billsService) {
        this.customersService = customersService;
        this.billsService = billsService;
    }

    //@Scheduled(cron = "0 40 6 * * *")

    //@Scheduled(cron = "0 * * * * *")
    //                 s  m h  d m a
    @Scheduled(cron = "0 47 10 * * *")
    public void scheduleTaskWithFixedRate() {
        logger.info("Extraction Task Started - ", dateTimeFormatter.format(LocalDateTime.now()) );
        List<Customers> customers = customersService.getCustomers();
        for(Customers customer : customers){
            try {
                billsService.extractUserDataFromSat(customer.getRfc(), customer.getPass());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
