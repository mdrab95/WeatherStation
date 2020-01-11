package com.weatherstation.rest;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class RestCall {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestCall.class);

    /**
     * Post data to thingspeak.com
     * @param ds18b20Data
     * @param bme280Data
     * @param decryptedPassword
     */
    void post(double[] ds18b20Data, double[] bme280Data, String decryptedPassword, long delayInSeconds) {
        Unirest.post("https://api.thingspeak.com/update.json")
                .header("accept", "application/json")
                .field("api_key", decryptedPassword) // api key
                .field("field1", ds18b20Data[0])
                .field("field2", ds18b20Data[1])
                .field("field3", ds18b20Data[2])
                .field("field4", ds18b20Data[3])
                .field("field5", bme280Data[0])
                .field("field6", bme280Data[1])
                .field("field7", bme280Data[2])
                .asJsonAsync(new Callback<JsonNode>() {
                    public void completed(HttpResponse<JsonNode> response) {
                        int code = response.getStatus();
                        JsonNode body = response.getBody();
                        InputStream rawBody = response.getRawBody();
                        LOGGER.info(String.format("Success: %s %s %s. Next update in %d s. ", code, body, rawBody, delayInSeconds));
                    }

                    public void failed(UnirestException e) {
                        LOGGER.error("The request has failed.");
                    }

                    public void cancelled() {
                        LOGGER.error("The request has been cancelled.");
                    }
                });
    }
}
