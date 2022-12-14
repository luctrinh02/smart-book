package com.dantn.bookStore.schedule;

import java.util.List;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.dantn.bookStore.entities.Bill;
import com.dantn.bookStore.entities.BillStatus;
import com.dantn.bookStore.services.BillService;
import com.dantn.bookStore.services.BillStatusService;
import com.dantn.bookStore.ultilities.BillStatusSingleton;

@Configuration
@EnableScheduling
public class BillSchedule {
	@Autowired
	private ObjectFactory<BillService> billService;
	@Autowired
	private ObjectFactory<BillStatusService> billStatusService;
	
	@Scheduled(cron = "0 0 0 * * *")
	public void complateBill() {
		BillStatus status=BillStatusSingleton.getInstance(billStatusService.getObject()).get(4);
		BillStatus status2=BillStatusSingleton.getInstance(billStatusService.getObject()).get(6);
		List<Bill> bills=billService.getObject().getBill(status);
		for(Bill x:bills) {
			x.setStatus(status2);
		}
		billService.getObject().saveAll(bills);
	}
}
