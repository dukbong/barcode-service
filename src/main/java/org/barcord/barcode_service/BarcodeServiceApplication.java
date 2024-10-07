package org.barcord.barcode_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BarcodeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarcodeServiceApplication.class, args);
	}

}
