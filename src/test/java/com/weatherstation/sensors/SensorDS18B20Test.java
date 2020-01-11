package com.weatherstation.sensors;

import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.io.w1.W1Master;
import com.pi4j.temperature.TemperatureScale;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SensorDS18B20Test {

    @Mock
    W1Master w1Master;

    @Mock
    TemperatureSensor temperatureSensor;

    @Test
    void shouldReturnCorrectTemperature() {
        when(temperatureSensor.getTemperature(TemperatureScale.CELSIUS)).thenReturn(20d);
        String[] sensors = {"1"};
        SensorDS18B20 ds18B20 = new SensorDS18B20();
        double[] temperatures = new double[sensors.length];

        ds18B20.setTemperatureByIndex(temperatures, temperatureSensor, sensors, 0);

        assertThat(temperatures)
                .hasSize(1)
                .containsOnly(20d);
    }

    @Test
    void shouldReturnCorrectTemperaturesArray() {
        List<TemperatureSensor> temperatureSensorList = new ArrayList<TemperatureSensor>() {{
            add(temperatureSensor);
            add(mock(TemperatureSensor.class));
        }};
        when(w1Master.getDevices(TemperatureSensor.class)).thenReturn(temperatureSensorList);
        when(temperatureSensorList.get(0).getName()).thenReturn("1");
        when(temperatureSensorList.get(0).getTemperature(TemperatureScale.CELSIUS)).thenReturn(20d);
        when(temperatureSensorList.get(1).getName()).thenReturn("2");
        when(temperatureSensorList.get(1).getTemperature(TemperatureScale.CELSIUS)).thenReturn(25d);
        String[] sensors = {"1", "2"};
        double[] temperatures = new double[sensors.length];
        SensorDS18B20 ds18B20 = new SensorDS18B20();

        temperatures = ds18B20.getData(sensors, temperatures, w1Master);

        assertThat(temperatures)
                .hasSize(2);
        assertThat(temperatures[0])
                .isEqualTo(20d);
        assertThat(temperatures[1])
                .isEqualTo(25d);
    }
}
