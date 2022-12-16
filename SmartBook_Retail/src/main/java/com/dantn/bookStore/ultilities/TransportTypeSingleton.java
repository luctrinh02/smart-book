package com.dantn.bookStore.ultilities;

import java.util.List;

import com.dantn.bookStore.entities.TransportType;
import com.dantn.bookStore.services.TransportTypeService;

public class TransportTypeSingleton {
	private static  List<TransportType> singleton;
	private TransportTypeSingleton() {};
	public static List<TransportType> getInstance(TransportTypeService service) {
		if (singleton==null) {
			singleton= service.getAll();
		}
		return singleton;
	}
}