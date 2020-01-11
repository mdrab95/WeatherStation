package com.weatherstation.sensors;

import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.io.w1.W1Master;
import com.pi4j.temperature.TemperatureScale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SensorDS18B20 {

    public static final Logger LOGGER = LoggerFactory.getLogger(SensorDS18B20.class);
    private List<TemperatureSensor> temperatureSensors;

    /**
     * This method reads temperature from all DS18B20 sensors.
     * @param sensors
     * @param tempArray
     * @param w1Master
     * @return array of temperatures from all DS18B20 sensors.
     */
    public double[] getData(String[] sensors, double[] tempArray, W1Master w1Master) {
        try {
            temperatureSensors = w1Master.getDevices(TemperatureSensor.class);
        } catch (Exception e) {
            LOGGER.error(String.format("Couldn't load list of devices. %s", e.getCause()));
        }
        if (temperatureSensors.size() != 0) {
            for (int i = 0; i < temperatureSensors.size(); i++) {
                for (int j = 0; j < sensors.length; j++) {
                    if (temperatureSensors.get(i).getName().contains(sensors[j])) {
                        setTemperatureByIndex(tempArray, temperatureSensors.get(i), sensors, j);
                    }
                }
            }
        }
        return tempArray;
    }

    void setTemperatureByIndex(double[] temperatures, TemperatureSensor device, String[] sensors, int index) {
        temperatures[index] = device.getTemperature(TemperatureScale.CELSIUS);
        LOGGER.info(String.format("Temperature from sensor [%s]: %.2f", sensors[index], temperatures[index]));
    }
}
