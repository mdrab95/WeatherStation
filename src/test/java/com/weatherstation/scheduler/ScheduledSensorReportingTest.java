package com.weatherstation.scheduler;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.w1.W1Master;
import com.weatherstation.rest.RestCall;
import com.weatherstation.rest.RestWrapper;
import com.weatherstation.sensors.SensorBME280;
import com.weatherstation.sensors.SensorDS18B20;
import com.weatherstation.util.PasswordDecrypter;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduledSensorReportingTest {

    @Mock
    PasswordDecrypter passwordDecrypter;

    @Mock
    RestCall restCall;

    @Mock
    RestWrapper restWrapper;

    @Mock
    W1Master w1Master;

    @Mock
    SensorDS18B20 ds18B20;

    @Mock
    SensorBME280 bme280;

    @Mock
    I2CBus i2CBus;

    @Test
    void shouldCallSendDataOverRestMethodAndThrowNoException() throws Exception {
        String[] ds18b20Sensors = {"1", "2", "3", "4"};
        when(passwordDecrypter.decrypt("lbwOb4nrsmW7GZZ2tgdE4zfK28eSYIEi")).thenReturn("password");
        when(ds18B20.getData(ds18b20Sensors, new double[ds18b20Sensors.length], w1Master)).thenReturn(new double[] {20.5, 19.5, 20, 50.5});
        when(bme280.getData(i2CBus.getDevice(0x76), new byte[24], new byte[8]))
                .thenReturn(new double[] {6.5, 980, 50});
        ScheduledSensorReporting scheduledSensorReporting = new ScheduledSensorReporting(passwordDecrypter,
                "lbwOb4nrsmW7GZZ2tgdE4zfK28eSYIEi", restCall, restWrapper, ds18B20, bme280, i2CBus, w1Master, ds18b20Sensors, 1);

        scheduledSensorReporting.run();

        verify(restWrapper, times(1))
                .sendDataOverRest(new double[] {20.5, 19.5, 20, 50.5}, new double[] {6.5, 980, 50}, "password", restCall, 1);
        assertThatCode(scheduledSensorReporting::run).doesNotThrowAnyException();
    }
}
