package com.weatherstation;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.w1.W1Master;
import com.weatherstation.rest.RestCall;
import com.weatherstation.rest.RestWrapper;
import com.weatherstation.scheduler.ScheduledSensorReporting;
import com.weatherstation.sensors.SensorBME280;
import com.weatherstation.sensors.SensorDS18B20;
import com.weatherstation.util.PasswordDecrypter;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;

@SpringBootApplication
public class WeatherstationApplication {

	private static final String[] DS18B20_SENSORS = {}; // Insert your ds18b20 sensors' names here
	private static final String ENCRYPTED_PASSWORD = ""; // Insert your encrypted password here (to generate it use PasswordEncrypter class
	private static final long DELAY_IN_SECONDS = 300;
	public static void main(String[] args) throws IOException, I2CFactory.UnsupportedBusNumberException {
		SpringApplication.run(WeatherstationApplication.class, args);
		PasswordDecrypter passwordDecrypter = new PasswordDecrypter(new BasicTextEncryptor());
		passwordDecrypter.initSaltAndPrepareBasicTextEncryptor(new Scanner(System.in));
		Timer time = new Timer();
		ScheduledSensorReporting ssr = new ScheduledSensorReporting(passwordDecrypter, ENCRYPTED_PASSWORD, new RestCall(),
				new RestWrapper(), new SensorDS18B20(), new SensorBME280(), I2CFactory.getInstance(I2CBus.BUS_1), new W1Master(), DS18B20_SENSORS);
		time.schedule(ssr, 0, DELAY_IN_SECONDS*1000); // new task every 5m (in milliseconds)
	}

}
