package com.ijse.media_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@SpringBootApplication
public class MediaServiceApplication {

	public static void main(String[] args) {
		loadDotenv();
		SpringApplication.run(MediaServiceApplication.class, args);
	}

	private static void loadDotenv() {
		String[] potentialPaths = {
			".env",
			"../.env",
			"../../.env",
			"d:/Enterprise cloud module/.env",
			System.getProperty("user.dir") + "/.env",
			System.getProperty("user.dir") + "/../.env",
			System.getProperty("user.dir") + "/../../.env"
		};
		for (String path : potentialPaths) {
			File file = new File(path);
			if (file.exists() && file.isFile()) {
				try {
					List<String> lines = Files.readAllLines(file.toPath());
					for (String line : lines) {
						String trimmed = line.trim();
						if (trimmed.isEmpty() || trimmed.startsWith("#")) continue;
						int eqIdx = trimmed.indexOf('=');
						if (eqIdx > 0) {
							String key = trimmed.substring(0, eqIdx).trim();
							String value = trimmed.substring(eqIdx + 1).trim();
							if ((value.startsWith("\"") && value.endsWith("\"")) ||
								(value.startsWith("'") && value.endsWith("'"))) {
								value = value.substring(1, value.length() - 1);
							}
							if (System.getProperty(key) == null && System.getenv(key) == null) {
								System.setProperty(key, value);
							}
						}
					}
					break;
				} catch (Exception ignored) {
				}
			}
		}
	}

}
