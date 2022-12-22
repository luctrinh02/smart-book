package com.dantn.bookStore.ultilities;

import java.io.IOException;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	public static String fileToBase64(MultipartFile file) throws IOException {
		byte[] fileByte=file.getBytes();
		String encode=Base64.getEncoder().encodeToString(fileByte);
		return encode;
	}
}
