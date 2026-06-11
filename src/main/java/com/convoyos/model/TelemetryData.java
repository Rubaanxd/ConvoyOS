package com.convoyos.model;

public class TelemetryData {

    private int gameId;
    private float speed;
    private float odometer;
    private String truckBrand;
    private String truckName;
    
    
    /**
     * ------------------------------------------------
     * ---------------- Setter & getters ----------------
     * ------------------------------------------------
     */

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

    public String getTruckBrand() {
        return truckBrand;
    }

    public void setTruckBrand(String truckBrand) {
        this.truckBrand = truckBrand;
    }

    public String getTruckName() {
        return truckName;
    }

    public void setTruckName(String truckName) {
        this.truckName = truckName;
    }
    
    
    /**
     * ------------------------------------------------
     * ---------------- Metodos Extras ----------------
     * ------------------------------------------------
     */


    
    public float getSpeedKmh() {
        return Math.abs(speed * 3.6f);
    }
    
    public boolean isMoving() {
        return getSpeedKmh() > 1.0f;
    }
}