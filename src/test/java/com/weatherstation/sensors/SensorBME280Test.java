package com.weatherstation.sensors;

import com.pi4j.io.i2c.I2CDevice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;


@ExtendWith(MockitoExtension.class)
public class SensorBME280Test {

    @Mock
    I2CDevice i2CDevice;

    @Test
    void shouldReturnCorrectData() throws IOException {
//        TODO: refactor bme280 class and finish this test
//        byte[] b1 = new byte[24];
//        byte[] dt = new byte[8];
//        when(i2CDevice.read(0x88, b1, 0, 24)).thenReturn(???);
//        when(i2CDevice.read(0xA1 & 0xFF)).thenReturn(???);
//        when(i2CDevice.read(0xE1, b1, 0, 7)).thenReturn(???);
//        when(i2CDevice.read(0xF7, dt, 0, 8)).thenReturn(???);
//
//        when(i2CDevice.read(0xF7, dt, 0, 8)).thenReturn(???);
//        SensorBME280 bme280 = new SensorBME280();
//
//        double[] data = bme280.getData(i2CDevice, b1, dt);
//
//        assertThat(data[0]).isEqualTo(20);
//        assertThat(data[1]).isEqualTo(1000);
//        assertThat(data[2]).isEqualTo(50);
    }
}
