package com.weatherstation.scheduler;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.w1.W1Master;
import com.weatherstation.rest.RestCall;
import com.weatherstation.rest.RestWrapper;
import com.weatherstation.sensors.SensorBME280;
import com.weatherstation.sensors.SensorDS18B20;
import com.weatherstation.util.PasswordDecrypter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.TimerTask;

public class ScheduledSensorReporting extends TimerTask {

    public static final Logger LOGGER = LoggerFactory.getLogger(ScheduledSensorReporting.class);
    private long delayInSeconds;
    private String decryptedPassword;
    private RestCall restCall;
    private PasswordDecrypter passwordDecrypter;
    private SensorDS18B20 ds18B20;
    private SensorBME280 bme280;
    private I2CBus i2CBus;
    private String url;
    private RestWrapper restWrapper;
    private W1Master w1Master;
    private String[] ds18b20sensors;

    public void prepare (PasswordDecrypter passwordDecrypter, String encryptedPassword, RestCall restCall, RestWrapper restWrapper, String url,
                                    SensorDS18B20 ds18B20, SensorBME280 bme280, I2CBus i2CBus, W1Master w1Master, String[] ds18b20sensors, long delayInSeconds) {
        this.passwordDecrypter = passwordDecrypter;
        this.restCall = restCall;
        this.restWrapper = restWrapper;
        this.url = url;
        this.w1Master = w1Master;
        this.ds18B20 = ds18B20;
        this.bme280 = bme280;
        this.ds18b20sensors = ds18b20sensors;
        this.i2CBus = i2CBus;
        this.delayInSeconds = delayInSeconds;
        decryptedPassword = passwordDecrypter.decrypt(encryptedPassword);
    }

    /**
     * This method gets data from all sensors and sends it to thingspeak.com
     * to use this task create Timer object and ScheduledSensorReporting object,
     * then use yourTimer.schedule(yourScheduledSensorReporting, delayInMilliseconds, periodInMilliseconds)
     */
    public void run() {
        System.out.println("--------------------------------------");
        LOGGER.info("Getting new data from sensors.");
        double[] ds18b20Data = new double[ds18b20sensors.length];
        try {
            ds18b20Data = ds18B20.getData(ds18b20sensors, ds18b20Data, w1Master);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        double[] bme280Data = {0, 0, 0};
        try {
            bme280Data = bme280.getData(i2CBus.getDevice(0x76), new byte[24], new byte[8]);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info("Sending data to thingspeak.com");
        restWrapper.sendDataOverRest(url, ds18b20Data, bme280Data, decryptedPassword, restCall, delayInSeconds); // sends new request
        System.out.println("--------------------------------------");
    }
}
