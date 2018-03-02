package com.weatherStation.sensors;
import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.io.w1.W1Master;
import com.pi4j.temperature.TemperatureScale;

import java.util.Arrays;

/**
 * Temperature Sensor (DS18B20) class.
 */
public class TemperatureSensorDS18B20 {
    /**
     * This method reads temperature from all sensors.
     * @return array of data from sensors
     */
    public double[] getTemperatureFromAllSensors(){
        double[] tempArray = new double[8];
        Arrays.fill(tempArray, 0);
        W1Master w1Master = new W1Master();
        for (TemperatureSensor device : w1Master.getDevices(TemperatureSensor.class)){
            if(device.getName().equals("28-0516b40980ff")){
                tempArray[0] = device.getTemperature(TemperatureScale.CELSIUS);
            }
        }
        return tempArray;
    }
}
