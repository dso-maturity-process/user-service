package com.governmentcio.dmp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(Application.class.getName());

	public static void main(String[] args) {

		LOG.info("Calling SpringApplication.run()...");

		SpringApplication.run(Application.class, args);
	}

}
