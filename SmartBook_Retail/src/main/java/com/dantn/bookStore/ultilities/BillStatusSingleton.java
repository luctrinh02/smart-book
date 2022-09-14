package com.dantn.bookStore.ultilities;

import java.util.List;

import com.dantn.bookStore.entities.BillStatus;
import com.dantn.bookStore.services.BillStatusService;

public class BillStatusSingleton {
	private static List<BillStatus> singleton;
	private BillStatusSingleton() {}
	public static List<BillStatus> getInstance(BillStatusService service){
		if(singleton==null) {
			singleton=service.getAll();
		}
		return singleton;
	}
}
