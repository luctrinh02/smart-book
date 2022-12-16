package com.dantn.bookStore.ultilities;

import java.util.List;

import com.dantn.bookStore.entities.PaymentType;
import com.dantn.bookStore.services.PaymentTypeService;

public class PaymentTypeSingleton {
	private static  List<PaymentType> singleton;
	private PaymentTypeSingleton() {};
	public static List<PaymentType> getInstance(PaymentTypeService service) {
		if (singleton==null) {
			singleton= service.getAll();
		}
		return singleton;
	}
}
