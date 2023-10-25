package com.employeeassign.emassignment.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime(){
        System.out.println("Scheduler is working "+ System.currentTimeMillis());
    }
}
