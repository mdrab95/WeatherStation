package com.weatherStation.scheduler;

import com.weatherStation.restapi.RESTCall;
import com.weatherStation.sensors.TemperatureSensorDS18B20;
import java.util.TimerTask;

/**
 * Task scheduler.
 */
public class ScheduledSensorReporting extends TimerTask {
    /**
     * This method gets data from all sensors and sends it to thingspeak.com
     * to use this task create Timer object and ScheduledSensorReporting object,
     * then use yourTimer.schedule(yourScheduledSensorReporting, delayInMilliseconds, periodInMilliseconds)
     */
    public void run(){
        System.out.println("--------------------------------------");
        double[] tempArray;
        TemperatureSensorDS18B20 sensor = new TemperatureSensorDS18B20();
        tempArray = sensor.getTemperatureFromAllSensors(); // reads temperature from all sensors
        for (int i = 0; i < 8; i++) {
            System.out.println("Temperature from sensor[" + (i + 1) + "]: " + tempArray[i]); // prints data from all sensors to your console
        }
        System.out.println("Sending data to thingspeak.com");
        RESTCall http = new RESTCall();
        http.sendDataOverRest(tempArray); // sends new request
        System.out.println("--------------------------------------");
    }
}
