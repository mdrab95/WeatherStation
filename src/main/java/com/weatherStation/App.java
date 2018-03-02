package com.weatherStation;

import com.weatherStation.scheduler.ScheduledSensorReporting;

import java.util.Timer;

/**
 * Application Main Class
 */
public class App {
    public static void main(String[] args) throws InterruptedException {
        Timer time = new Timer();
        ScheduledSensorReporting ssr = new ScheduledSensorReporting();
        time.schedule(ssr, 0, 30000); // new task every 30s (in milliseconds)
    }
}
