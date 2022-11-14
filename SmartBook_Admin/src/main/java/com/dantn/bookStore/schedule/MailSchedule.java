package com.dantn.bookStore.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class MailSchedule {
    @Scheduled(cron = "0 0 0 * * ?")
    public void sendMail() {
        System.out.println("Gửi mail......");
    }
    @Scheduled(cron = "0 * * ? * *")
    public void demo() {
        System.out.println("Đây là test mỗi phút 1 lần......");
    }
}
