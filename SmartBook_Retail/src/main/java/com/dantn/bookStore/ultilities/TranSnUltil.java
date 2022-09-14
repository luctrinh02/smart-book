package com.dantn.bookStore.ultilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TranSnUltil {
	public static String getTranSn() {
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return format.format(new Date());
	}
}
