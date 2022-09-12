package com.dantn.bookStore.ultilities;

import java.util.HashMap;

public class DataUltil {
	public static HashMap<String, Object> setData(String statusCode,Object data) {
		HashMap<String, Object> map=new HashMap<>();
		map.put("statusCode", statusCode);
		map.put("data", data);
		return map;
	}
}
