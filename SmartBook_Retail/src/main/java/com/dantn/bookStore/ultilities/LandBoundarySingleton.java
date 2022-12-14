package com.dantn.bookStore.ultilities;

import java.util.HashMap;
import java.util.List;

import com.dantn.bookStore.entities.City;
import com.dantn.bookStore.entities.District;
import com.dantn.bookStore.services.CityService;

public class LandBoundarySingleton {
	private static  HashMap<String, Object> singleton;
	private LandBoundarySingleton() {};
	public static HashMap<String, Object> getInstance(CityService service) {
		if (singleton==null) {
			singleton = getData(service.getAll());
		}
		return singleton;
	}
	private static HashMap<String, Object> getData(List<City> listCity){
		HashMap<String, Object> mapReturn = new HashMap<String, Object>();
		HashMap<String, Object> mapDistrict = new HashMap<String, Object>();
		HashMap<String, Object> mapWard = new HashMap<String, Object>();
		for (City city : listCity) {
			mapDistrict.put(city.getId().toString(), city.getDistricts());
			for (District district : city.getDistricts()) {
				mapWard.put(district.getId().toString(), district.getWards());
			}
		}
		mapReturn.put("citys", listCity);
		mapReturn.put("districts", mapDistrict);
		mapReturn.put("wards", mapWard);
		return mapReturn;
	} 
}
