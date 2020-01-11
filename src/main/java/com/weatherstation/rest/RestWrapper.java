package com.weatherstation.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestWrapper {

    public static final Logger LOGGER = LoggerFactory.getLogger(RestWrapper.class);

    /**
     * This method creates and sends request to thingspeak.com
     * @param ds18b20Data
     * @param bme280Data
     */
    public void sendDataOverRest(double[] ds18b20Data, double[] bme280Data, String decryptedPassword, RestCall restCall, long delayInSeconds) {
        LOGGER.info("Sending new request.");
        restCall.post(ds18b20Data, bme280Data, decryptedPassword, delayInSeconds);
    }
}
