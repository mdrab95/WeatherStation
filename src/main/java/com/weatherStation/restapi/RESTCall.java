package com.weatherStation.restapi;

import java.io.InputStream;
import java.util.Date;

import com.mashape.unirest.http.*;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * RESTCall class.
 */
public class RESTCall {
    Date date;

    /**
     * This method creates and sends request to thingspeak.com
     * @param variableArray - array with data from sensors
     */
    public void sendDataOverRest(double[] variableArray) {
        Unirest.post("https://api.thingspeak.com/update.json")
                .header("accept", "application/json")
                .field("api_key", "XXXXXXXXXXXXXXXX") // api key
                .field("field1", variableArray[0])
                /*
                .field("field2", variableArray[1])
                .field("field3", variableArray[2])
                .field("field4", variableArray[3])
                .field("field5", variableArray[4])
                .field("field6", variableArray[5])
                .field("field7", variableArray[6])
                .field("field8", variableArray[7])
                */
                .asJsonAsync(new Callback<JsonNode>() {
                    public void completed(HttpResponse<JsonNode> response) {
                        int code = response.getStatus();
                        JsonNode body = response.getBody();
                        InputStream rawBody = response.getRawBody();
                        date = new Date();
                        System.out.println("Sending new request (" + date + "):");
                        System.out.println(code);
                        System.out.println(body);
                        System.out.println(rawBody);
                        System.out.println("Success! Next update in 30s.");
                    }

                    public void failed(UnirestException e) {
                        System.out.println("The request has failed");
                    }

                    public void cancelled() {
                        System.out.println("The request has been cancelled");
                    }
                });
    }
}
