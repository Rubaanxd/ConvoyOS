package com.convoyos.model;

public class TelemetryData {

    private int gameId;
    private float speed;
    private float odometer;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getOdometer() {
        return odometer;
    }

    public void setOdometer(float odometer) {
        this.odometer = odometer;
    }
    
    public float getSpeedKmh() {
        return Math.abs(speed * 3.6f);
    }
}