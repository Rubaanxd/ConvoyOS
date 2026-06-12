package com.convoyos.stats;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class SessionStats {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private float startOdometer;
    private float currentOdometer;
    private float previousOdometer;
    private float accumulatedDistance;

    private long startTime;
    private long endTime;
    private long drivingTimeSeconds;

    private float speedSum;
    private long speedSamples;

    public void start(float odometer) {
        startOdometer = odometer;
        currentOdometer = odometer;
        previousOdometer = odometer;
        accumulatedDistance = 0;
        drivingTimeSeconds = 0;
        speedSum = 0;
        speedSamples = 0;
        startTime = System.currentTimeMillis();
    }

    public void update(float odometer, boolean moving, float speedKmh) {

        endTime = System.currentTimeMillis();

        float delta = odometer - previousOdometer;

        if (delta > 0) accumulatedDistance += delta;

        previousOdometer = odometer;
        currentOdometer = odometer;

        if (moving) {
            drivingTimeSeconds++;
            speedSum += speedKmh;
            speedSamples++;
        }
    }

    /**
     * ------------------------------------------------
     * ---------------- Getters ----------------
     * ------------------------------------------------
     */
    public float getStartOdometer() {
        return startOdometer;
    }

    public float getCurrentOdometer() {
        return currentOdometer;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getDrivingTimeSeconds() {
        return drivingTimeSeconds;
    }

    /**
     * ------------------------------------------------
     * ---------------- Métodos Calculados ----------------
     * ------------------------------------------------
     */
    public float getDistanceTravelled() {
        return accumulatedDistance;
    }

    public long getSessionDurationSeconds() {
        return (System.currentTimeMillis() - startTime) / 1000;
    }

    public long getSessionDurationMinutes() {
        return getSessionDurationSeconds() / 60;
    }

    public long getSessionDurationHours() {
        return getSessionDurationMinutes() / 60;
    }

    public float getAverageSpeed() {
        if (speedSamples == 0) return 0;
        return speedSum / speedSamples;
    }

    /**
     * ------------------------------------------------
     * ---------------- Métodos Formateados ----------------
     * ------------------------------------------------
     */
    public String getDistanceFormatted() {
        return String.format("%,.1f km", getDistanceTravelled());
    }

    public String getSessionDurationFormatted() {

        long totalSeconds = getSessionDurationSeconds();

        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        return String.format("%02d:%02d:%02d",
                hours,
                minutes,
                seconds);
    }

    public String getDrivingTimeFormatted() {

        long hours = drivingTimeSeconds / 3600;
        long minutes = (drivingTimeSeconds % 3600) / 60;
        long seconds = drivingTimeSeconds % 60;

        return String.format("%02d:%02d:%02d",
                hours,
                minutes,
                seconds);
    }

    public String getAverageSpeedFormatted() {
        return String.format("%.1f km/h", getAverageSpeed());
    }

    public String getStartOdometerFormatted() {
        return String.format("%,.1f km", startOdometer);
    }

    public String getCurrentOdometerFormatted() {
        return String.format("%,.1f km", currentOdometer);
    }

    public String getStartDateFormatted() {

        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(startTime),
                ZoneId.systemDefault())
                .format(DATE_FORMAT);
    }

    public String getEndDateFormatted() {

        if (endTime == 0) return "-";

        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(endTime),
                ZoneId.systemDefault())
                .format(DATE_FORMAT);
    }
}