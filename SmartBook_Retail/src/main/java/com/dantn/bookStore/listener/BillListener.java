package com.dantn.bookStore.listener;

import java.util.List;

import javax.persistence.PostUpdate;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.dantn.bookStore.entities.Bill;
import com.dantn.bookStore.entities.BillDetail;
import com.dantn.bookStore.services.UserBuyService;
import com.dantn.bookStore.services.UserClickService;

@Configurable
public class BillListener {
	@Autowired
	private ObjectFactory<UserClickService> factory;
	@Autowired
	private ObjectFactory<UserBuyService> factory2;
	@PostUpdate
	public void update(Bill bill) {
		List<BillDetail> list = bill.getDetails();
		switch (bill.getStatus().getId()) {
		case 3:
			for (BillDetail detail : list) {
				factory.getObject().deleteByBook(detail.getBook());
			}
			break;
		default:
			break;
		}
		
	}
}
