package com.jah.Zimple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZimpleApplication {

	public static void main(String[] args) {
		System.out.println("Starting app...");
		//DBModel db = new DBModel();
		//db.connecttoKube();
		//db.selectTable();
		//db.select();

		SpringApplication.run(ZimpleApplication.class, args);
	}

}
